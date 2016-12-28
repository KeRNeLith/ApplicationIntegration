#!/bin/bash


# Step 1 : Rechercher si Java est installé.
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
fi

echo

# Step 2 : Installation de Payara.
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
            # TODO - Décompresser et supprimer l'archive


            break;;
        [Nn]*)
            echo "Passage à l'étape suivante...'"
            break;;
        *)
            echo "Veuillez répondre y ou n";;
    esac
done

# TODO : mise en place des variables Payara

# TODO : configuration MySQL

# TODO : ajout artefact

# Lancement firefox ou Chrome

echo "Sortie du programme de configuration..."


