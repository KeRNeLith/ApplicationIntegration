#!/bin/bash

# Exécutables requis pour l'exécution de ce script
echo
echo "Bienvenue dans l'assistant de déploiement de l'application doctorOffice."
echo
echo "Pour le bon déroulement de ce programme d'installation, les programmes suivants doivent être installés sur votre machine : "
echo "  - java"
echo "  - unzip"
echo "  - tar"

# Etape 1 : Rechercher si Java et unzip sont installés.
echo
echo "----------------------------------------"
echo "Recherche d'une installation de Java"
echo "----------------------------------------"
echo

if type -p java; then
    echo "Installation de Java trouvée."
    _java=java
# Installation de Java trouvée via $JAVA_HOME.
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo "Installation de Java trouvée."     
    _java="$JAVA_HOME/bin/java"
else
    echo "Aucune installation de Java trouvée."
    echo "Sortie de l'assistant..."
    exit
fi

echo
echo "----------------------------------------"
echo "Recherche d'une installation de unzip"
echo "----------------------------------------"
echo

if type -p unzip; then
    echo "Installation de unzip trouvée."
else
    echo "Aucune installation de unzip trouvée."
    echo "Sortie de l'assistant..."
    exit
fi

# Etape 2 : Installation et configuration de MySQL
echo
echo "----------------------------------------"
echo "Installation de MySQL"
echo "----------------------------------------"
echo

while true; do
    read -p "Avez-vous besoin d'installer MySQL ? (y/n) " yn
    case $yn in
        [Yy]*)
            # Installation de MySQL
            echo "Lancement de l'installation de MySQL (requiert mot de passe root)"
            sudo apt-get install -y mysql-server
            break;;
        [Nn]*)
            break;;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done

# Récupération du mot de passe root
read -p "Veuillez entre votre mot de passe administrateur pour MySQL : " -s mysqlpwd
echo

# Création de la base de données
while true; do
    read -p "Si elle existe, la base de données doctorOffice va être supprimée. Est-ce que cela vous convient ? (y/n) " yn
    case $yn in
        [Yy]*)
            # Suppression de la base de données
            echo "Suppression de la base de données"
            mysql --user=root --password=$mysqlpwd --execute='DROP DATABASE doctorOffice;'
            # Création de la base de données
            echo "Creation de la base de données"
            mysql --user=root --password=$mysqlpwd --execute='CREATE DATABASE doctorOffice;'
            break;;
        [Nn]*)
            echo "Merci de sauvegarder cette base de données pour que nous puissons la remplacer."
            echo "Sortie de l'assistant..."
            exit
            break;;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done

# Création d'un utilisateur dédié
echo "Creation d'un utilisateur MySQL..."
mysql --user=root --password=$mysqlpwd --execute='CREATE USER "appJEE"@"localhost" IDENTIFIED BY "password";'
mysql --user=root --password=$mysqlpwd --execute='GRANT CREATE, DROP, DELETE, INSERT, SELECT, UPDATE ON doctorOffice.* TO "appJEE"@"localhost";'

# Etape 3 : Installation et configuration de Payara.
echo
echo "----------------------------------------"
echo "Installation de Payara"
echo "----------------------------------------"
echo

# On boucle jusqu'à qu'il y ait une réponse correcte.
while true; do
    read -p "Voulez-vous installer Payara ? (y/n) " yn
    case $yn in
        [Yy]*)
            # Installation et téléchargement de Payara.
            echo "Installation de Payara en cours..."
            echo "Lancement du téléchargement de Payara"
            wget https://s3-eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/Payara+4.1.1.164/payara-4.1.1.164.zip
            unzip payara-4.1.1.164.zip
            rm payara-4.1.1.164.zip
            asadmin=./payara41/bin/asadmin
            break;;
        [Nn]*)
            read -p "Merci d'entrer le chemin vers l'executable asadmin (ex: /usr/lib/payara/bin) : " asadmin
            asadmin="$asadmin/asadmin"
            break;;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done

# Importation de la librairie MySQL
echo
echo "----------------------------------------"
echo "Importation de la librairie MySQL"
echo "----------------------------------------"
echo

wget http://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-5.1.40.tar.gz
tar -xvf mysql-connector-java-5.1.40.tar.gz
# Copie du jar
cp ./mysql-connector-java-5.1.40/mysql-connector-java-5.1.40-bin.jar ./payara41/glassfish/lib/
rm -r mysql-connector-java-5.1.40
rm mysql-connector-java-5.1.40.tar.gz

# Lancement du serveur
echo
echo "----------------------------------------"
echo "Lancement du serveur"
echo "----------------------------------------"
echo

$asadmin start-domain

echo "Serveur lancé"

# Configuration de Payara
echo
echo "----------------------------------------"
echo "Configuration de Payara"
echo "----------------------------------------"
echo

read -p "Enter the name of the Payara admin account : " asadminlogin
read -p "Enter the password of the Payara admin account : " -s asadminpwd
echo "AS_ADMIN_PASSWORD=$asadminpwd" > credentials.dat

# Configuration du connection pool
$asadmin --passwordfile credentials.dat create-jdbc-connection-pool --datasourceclassname "com.mysql.jdbc.jdbc2.optional.MysqlDataSource" --restype "javax.sql.DataSource" --property User=appJEE:Password=password:URL="jdbc\:mysql\://localhost\:3306/doctorOffice":Url="jdbc\:mysql\://localhost\:3306/doctorOffice" DoctorOffice

# Configuration de la ressource
$asadmin --passwordfile credentials.dat create-jdbc-resource --connectionpoolid DoctorOffice doctoroffice

echo "Payara configuré."

rm credentials.dat

# Etape 4 : Déploiement de l'artéfact
echo
echo "----------------------------------------"
echo "Déploiement application"
echo "----------------------------------------"
echo

echo "Déploiement de l'application doctorOffice"
$asadmin deploy ./doctorOffice.war

echo "L'application est lancée, rendez-vous sur http://localhost:8080/doctorOffice/"
echo

# Etape 5 : Arrêt du serveur
read -p "Pour arrêter le serveur, appuyez sur une touche..."

while true; do
    read -p "Voulez-vous arrêter le serveur ? (y/n) " yn
    case $yn in
        [Yy]*)
            # Arrêt du serveur
            echo "Arrêt de Payara"
            $asadmin stop-domain
            echo "Serveur arrêté."
            echo "Sortie de l'assistant..."
            exit
            break;;
        [Nn]*)
            read -p "Pour arrêter le serveur, appuyez sur une touche...";;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done
