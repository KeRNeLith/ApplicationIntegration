#!/bin/bash

# Exécutables requis pour l'exécution de ce script
echo
echo "Bienvenue dans l'installation de l'application doctorOffice."
echo
echo "Pour le bon déroulement de ce programme d'installation, les programmes suivants doivent être installés sur votre machine : "
echo "  - Java"
echo "  - Unzip"

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
    echo "Sortie du programme..."
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
    echo "Sortie du programme..."
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
            echo "Creation de la base de données"
            mysql --user=root --password=$mysqlpwd --execute='CREATE DATABASE doctorOffice;'
            break;;
        [Nn]*)
            echo "Merci de sauvegarder cette base de données pour que nous puissons la remplacer."
            echo "Sortie du programme..."
            exit
            break;;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done

# Création d'un utilisateur dédié
echo "Creation d'un utilisateur MySQL..."
mysql --user=root --password=$mysqlpwd --execute='CREATE USER "appJEE"@"localhost" IDENTIFIED BY "password";'
mysql --user=root --pasword=$mysqlpwd --execute='GRANT CREATE, DROP, DELETE, INSERT, SELECT, UPDATE ON doctorOffice.* TO "appJEE"@"localhost";'

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
            # wget https://s3-eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/Payara+4.1.1.164/payara-4.1.1.164.zip
            unzip payara-4.1.1.164.zip
            # rm payara-4.1.1.164.zip
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

echo
echo "----------------------------------------"
echo "Mise en place des variables"
echo "----------------------------------------"
echo

read -p "Enter the name of the admin account : " asadminlogin
read -p "Enter the password of the admin account : " -s asadminpwd
echo "AS_ADMIN_PASSWORD=$asadminpwd" > credentials.dat

# Configuration du connection pool
$asadmin --passwordfile credentials.dat create-jdbc-connection-pool --datasourceclassname "com.mysql.jdbc.jdbc2.optional.MysqlDataSource" --restype "javax.sql.DataSource" --property User=appJEE:Password=password:URL="jdbc\:mysql\://localhost\:3306/doctorOffice":Url="jdbc\:mysql\://localhost\:3306/doctorOffice" DoctorOffice



# TODO : ajout artefact

# Lancement firefox ou Chrome

echo "Sortie du programme de configuration..."


