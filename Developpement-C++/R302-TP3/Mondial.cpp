/* 
 * File:   Mondial.cpp
 * Author: hb
 * 
 * Created on 22 novembre 2018, 16:05
 */

#include "Mondial.h"


#include <iostream>     // pour cout
#include <iomanip>      // pour setw()
#include <sstream>
#include <iterator>

Mondial::Mondial(const char* filename) {
    // Chargement du fichier XML en mémoire
    imageMondial.LoadFile(filename);
    // Initialisation de l'attribut racineMondial avec la racine (élément <mondial>)
    racineMondial = imageMondial.FirstChildElement();
}

void Mondial::Print() {
    imageMondial.Print();
}

/*
 * FOURNIE
 */
int Mondial::getNbAirports() const {
    // initialisation du nombre d’aéroports
    int nb = 0;
    // accéder à <airportscategory>, c’est un fils de l'élément <racineMondial>
    XMLElement* airportsCategory = racineMondial->FirstChildElement("airportscategory");
    // parcours complet des fils de <airportscategory> en les comptants
    // 1) accéder au premier fils <airport> de <airportscategory>
    XMLElement* currentAirport = airportsCategory->FirstChildElement();
    // 2) parcourir tous les <airport> qui sont des frères
    while (currentAirport != nullptr) {
        // un aéroport supplémentaire
        nb = nb + 1;
        // avancer au frère <airport> suivant de currentAirport
        currentAirport = currentAirport->NextSiblingElement();
    }
    // currentAirport n’a plus de frère {currentAirport == nullptr}, c’est le dernier
    return nb;
}

/*
 * FOURNIE
 */
void Mondial::printCountriesCode() const {
    int rank = 1; // rang du pays
    string carcodeValue; // valeur de l'attribut "car_cod" du pays courant
    // accéder à <countriescategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* countriesCategory = racineMondial->FirstChildElement("countriescategory");
    // parcours complet des fils de <countriescategory> en affichant le rang et le code
    // 1) accéder au premier fils <country> de <countriescategory>
    XMLElement* currentCountry = countriesCategory->FirstChildElement();
    // 2) parcourir tous les <country> qui sont des frères
    while (currentCountry != nullptr) {
        // traiter le pays courant
        //      1) récupérer la valeur de l’attribut "car_code"
        carcodeValue = currentCountry->Attribute("car_code");
        //      2) faire l’affichage
        cout << setw(5) << rank << " : " << carcodeValue << endl;
        // avancer au frère <country> suivant de currentCountry
        currentCountry = currentCountry->NextSiblingElement();
        // mettre à jour le rang
        rank = rank + 1;
    }
    // currentCountry n’a pas de frère {currentCountry == nullptr}, c’est fini
}


/*
 * A COMPLETER
 */
int Mondial::getNbDeserts() const {
    // initialisation du nombre de déserts
    int nb = 0;
    // accéder à <desertscategory>, c’est un fils de l'élément <racineMondial>
    XMLElement* desertsCategory = racineMondial->FirstChildElement("desertscategory");
    // parcours complet des fils de <desertscategory> en les comptants
    // 1) accéder au premier fils <desert> de <desertscategory>
    XMLElement* currentDesert = desertsCategory->FirstChildElement();
    // 2) parcourir tous les <desert> qui sont des frères
    while (currentDesert != nullptr) {
        // un désert supplémentaire
        nb = nb + 1;
        // avancer au frère <airport> suivant de currentAirport
        currentDesert = currentDesert->NextSiblingElement();
    }
    // currentDesert n’a plus de frère {currentDesert == nullptr}, c’est le dernier
    return nb;
}

/*
 * A COMPLETER
 */
int Mondial::getNbElemCat(const string categoryName) {
    // décoder le categoryName vers le nom de la balise XML
    string XMLElementName = decod_category[categoryName];
    /*
     * l'accès à la catégorie demandée dans les données se fera avec
     * racineMondial->FirstChildElement(XMLElementName.c_str())
     */
    
    // initialiser le compteur
    int nb = 0;
    // accéder à <NameCategory>
    // attention il faut convertir le XMLElement de type string en un type char*
    XMLElement* theCategory = racineMondial->FirstChildElement(XMLElementName.c_str());
    // parcours complet des fils de <theCategory> en les comptants
    // 1) accéder au premier fils <theCategory>
    XMLElement* currentElem = theCategory->FirstChildElement();
    // 2) parcourir tous les frères
    while (currentElem != nullptr) {
        // un frère supplémentaire
        nb = nb + 1;
        // avancer au frère suivant
        currentElem = currentElem->NextSiblingElement();
    }
    // currentElem n’a plus de frère {currentDesert == nullptr}, c’est le dernier
    return nb;
}

/*
 * A COMPLETER
 */
XMLElement* Mondial::getCountryXmlelementFromNameRec(string countryName) const {
    // atteindre le premier pays <country>
    // accéder à <countriescategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* countriesCategory = racineMondial->FirstChildElement("countriescategory");
    // appeler le worker sur le premier élément <country>
    return getCountryXmlelementFromNameRecWorker(countriesCategory->FirstChildElement(), countryName);
}

/*
 * A COMPLETER
 */
XMLElement* Mondial::getCountryXmlelementFromNameRecWorker(XMLElement* currentCountryElement, string countryName) const {
    if (currentCountryElement == nullptr) {
        // si il n'y pas de pays on retourn un pointeur nullptr
        return currentCountryElement;
    } else if (currentCountryElement->FirstChildElement("name")->GetText() == countryName) {
        // si on est sur le bon pays on retourne une pointeur sur l'élément courant
        return currentCountryElement;
    } else {
        // si on n'est pas sur le bon pays, aller sur le pays suivant (frère)
        return getCountryXmlelementFromNameRecWorker(currentCountryElement->NextSiblingElement(), countryName);
    }
}

/*
 * A COMPLETER
 */
string Mondial::getCountryCodeFromName(string countryName) const throw (PrecondVioleeExcep) {
    XMLElement* theCountryXMLElement = getCountryXmlelementFromNameRec(countryName);
    if (theCountryXMLElement) {
        return theCountryXMLElement->Attribute("car_code");
    } else {
        string message = "Dans getCountryCodeFromName, le pays " + countryName + " n'existe pas !";
        throw (PrecondVioleeExcep(message));
    }
}

/*
 * A COMPLETER
 */
/**
 * élément <country> d'un pays identifié par son nom countryName
 * @param countryName
 * @return pointeur sur l'élément <country> dont la valeur du fils <name> est égal à countryName, nullprt sinon
 */
XMLElement* Mondial::getCountryXmlelementFromNameIter(string countryName) const {
    // accéder à <countriescategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* countriesCategory = racineMondial->FirstChildElement("countriescategory");
    // accéder au premier pays : premier fils <country> de <countriescategory>
    XMLElement* currentCountry = countriesCategory->FirstChildElement();
    // parcourir (partiel) les <country> tantque le fils <name> est différent de countryName
    while ((currentCountry != nullptr) && (currentCountry->FirstChildElement("name")->GetText() != countryName)) {
        // avancer au frère <country> suivant de currentCountry
        currentCountry = currentCountry->NextSiblingElement();
    }
    // (currentCountry == nullptr) || (currentCountry->FirstChildElement("name")->GetText() == countryName)
    // on retourne l'élément courant dans tous les cas
    return currentCountry;
}

/*
 * A COMPLETER
 */
int Mondial::getCountryPopulationFromName(string countryName) const {
    // acceder au pays countryName
    XMLElement* theCountry = getCountryXmlelementFromNameIter(countryName);
    // si countryName est présent accéder à la dernière population renseignée
    if (theCountry) {
        // 1) soit on accède au premier fils <population> et on les parcours jusqu'au dernier
        /*
        XMLElement* theLastPopulation = theCountry->FirstChildElement("population");
        while (theLastPopulation->NextSiblingElement("population")) {
            theLastPopulation = theLastPopulation->NextSiblingElement("population");
        }
        // theLastPopulation est la dernière population renseignée
         */
        // 2) soit on accède au dernier fils <population> et c'est tout de suite le bon
        XMLElement* theLastPopulation = theCountry->LastChildElement("population");
        // par mesure de précaution on regarde si il y a bien un element <population>
        if (theLastPopulation) {
            return stoi(theLastPopulation->GetText());
        } else {
            return 0;
        }
    } else {
        return -1;
    }
}

/*
 * A COMPLETER
 */
XMLElement* Mondial::getCountryXmlelementFromCode(string countryCode) const {
    // accéder à <countriescategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* countriesCategory = racineMondial->FirstChildElement("countriescategory");
    // accéder au premier pays : premier fils <country> de <countriescategory>
    XMLElement* currentCountry = countriesCategory->FirstChildElement();
    // parcourir les <country> tantque son fils <name> est différent de countryName
    while ((currentCountry != nullptr) && (currentCountry->Attribute("car_code") != countryCode)) {
        // avancer au frère <country> suivant de currentCountry
        currentCountry = currentCountry->NextSiblingElement();
    }
    // (currentCountry == nullptr) || (currentCountry->FirstChildElement("name")->GetText() == countryName)
    // on retourne l'élément courant dans tous les cas
    return currentCountry;
}

/*
 * A COMPLETER
 */
void Mondial::printCountryBorders(string countryName) const {
    // acceder au pays countryName
    XMLElement* theCountry = getCountryXmlelementFromNameIter(countryName);
    // si countryName est présent parcourir les éléments <border>
    if (theCountry) {
        // accéder au premier élément <border>
        XMLElement* currentBorder = theCountry->FirstChildElement("border");
        // le pays a-il des pays frontaliers ?
        if (currentBorder) { // oui
            cout << "Le pays : " << countryName << endl;
            // parcourir tous les éléments <border>
            while (currentBorder) { // traiter le pays courant
                // obtenir le code du pays frontalier
                string theBorderCountryCode = currentBorder->Attribute("country");
                // obtenir l'élément <country> du pays concerné
                XMLElement* theBorderCountry = getCountryXmlelementFromCode(theBorderCountryCode);
                // afficher le nom du pays frontalier et la longueur de la frontière
                cout << " est frontalier avec : " << theBorderCountry->FirstChildElement("name")->GetText();
                cout << ", la longueur de sa frontière avec celui-ci est : " << currentBorder->Attribute("length") << endl;
                // avancer sur le <border> suivant (le frère peu être autre chose !)
                currentBorder = currentBorder->NextSiblingElement("border");
            }
        } else {
            cout << "Le pays : " << countryName << ", n'a pas de pays frontalier !" << endl;
        }
    } else {
        cout << "Le pays : " << countryName << ", n'existe pas !" << endl;
    }
}

/*
 * A COMPLETER
 */
XMLElement* Mondial::getRiverXmlelementFromNameIter(string riverName) const {
    // accéder à <riverscategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* riversCategory = racineMondial->FirstChildElement("riverscategory");
    // accéder au premier fleuve : premier fils <river> de <riverscategory>
    XMLElement* currentRiver = riversCategory->FirstChildElement();
    // parcourir les <river> tantque son fils <name> est différent de riverName
    while ((currentRiver != nullptr) && (currentRiver->FirstChildElement("name")->GetText() != riverName)) {
        // avancer au frère <country> suivant de currentCountry
        currentRiver = currentRiver->NextSiblingElement();
    }
    // (currentRiver == nullptr) || (currentRiver->FirstChildElement("name")->GetText() == riverName)
    // on retourne l'élément courant dans tous les cas
    return currentRiver;
}

/*
 * A COMPLETER
 */
void Mondial::printAllCountriesCrossedByRiver(string riverName) const {
    // accéder à l'élément <river> de <name> riverName
    XMLElement* theRiver = getRiverXmlelementFromNameIter(riverName);
    // vérifier que le fleuve est là avant de faire l'affichage
    if (theRiver) {
        // le fleuve est présent
        cout << "Le fleuve : " << riverName << endl;
        // accéder à la valeur de l'attribut country de la balise <river>
        string theCountriesString = theRiver->Attribute("country");
        // produire une liste de codes de pays dans un vecteur par exemple
        vector<string> theCountryCodes = split(theCountriesString, ' ');
        // parcourir le vecteur theCountryCodes, aller chercher les noms de pays et les afficher
        // sauf le dernier pour faire un affichage joli
        XMLElement* currentCountry;
        cout << " traverse les pays suivants : ";
        for (vector<string>::size_type i = 0; i != theCountryCodes.size() - 1; i++) {
            currentCountry = getCountryXmlelementFromCode(theCountryCodes[i]);
            if (currentCountry) {
                cout << currentCountry->FirstChildElement("name")->GetText() << ", ";
            }
        }
        // afficher le dernier pays
        currentCountry = getCountryXmlelementFromCode(theCountryCodes[theCountryCodes.size() - 1]);
        cout << currentCountry->FirstChildElement("name")->GetText();
        // afficher aussi la longueur du fleuve
        cout << " ; il a la longueur suivante : " << theRiver->FirstChildElement("length")->GetText() << endl;
    } else {
        cout << "Le fleuve : " << riverName << ", n'existe pas !" << endl;
    }
}

/*
 * A COMPLETER
 */
void Mondial::printCountriesWithProvincesCrossedByRiver(string riverName) const {
    // accéder à l'élément <river> de <name> riverName
    XMLElement* theRiver = getRiverXmlelementFromNameIter(riverName);
    // vérifier que le fleuve est là avant de faire l'affichage
    if (theRiver) {
        // le fleuve est présent
        cout << "Le fleuve : " << riverName << endl;
        // parcourir tous les éléments <located> sauf le dernier pour faire un affichage joli
        // accéder au premier <located>
        XMLElement* currentLocated = theRiver->FirstChildElement("located");
        string currentCountryCode;
        XMLElement* currentCountry;
        cout << " traverse les pays suivants : ";
        while (currentLocated->NextSiblingElement("located")) {
            // obtenir le nom du pays de currentLocated
            currentCountryCode = currentLocated->Attribute("country");
            // obtenir l'élément <country> qui correspond à currentCountryCode
            currentCountry = getCountryXmlelementFromCode(currentCountryCode);
            // afficher le nome du pays s'il existe (il doit normalement exister, programmation défensive
            if (currentCountry) {
                cout << currentCountry->FirstChildElement("name")->GetText() << ", ";
            }
            // avancer
            currentLocated = currentLocated->NextSiblingElement("located");
        }
        // afficher le dernier pays frontalier
        // obtenir le nom du pays de currentLocated
        currentCountryCode = currentLocated->Attribute("country");
        // obtenir l'élément <country> qui correspond à currentCountryCode
        currentCountry = getCountryXmlelementFromCode(currentCountryCode);
        cout << currentCountry->FirstChildElement("name")->GetText();
        // afficher aussi la longueur du fleuve
        cout << " ; il a la longueur suivante : " << theRiver->FirstChildElement("length")->GetText() << endl;
    } else {
        cout << "Le fleuve : " << riverName << ", n'existe pas !" << endl;
    }
}

/*
 * A COMPLETER
 */
void Mondial::printCountriesAndProvincesCrossedByRiver(string riverName) const {
    // accéder à l'élément <river> de <name> riverName
    XMLElement* theRiver = getRiverXmlelementFromNameIter(riverName);
    // vérifier que le fleuve est là avant de faire l'affichage
    if (theRiver) {
        // le fleuve est présent son nom et sa longueur
        // le fleuve est présent son nom et sa longueur
        cout << "Le fleuve " << riverName << " de longueur " << theRiver->FirstChildElement("length")->GetText() << " traverse les pays suivants : " << endl;
        // accéder à la valeur de l'attribut country de la balise <river>
        string theCountriesString = theRiver->Attribute("country");
        // produire une liste de codes de pays dans un vecteur par exemple
        vector<string> theCountryCodes = split(theCountriesString, ' ');
        // produire pour les pays avec province une map<car_code, "liste de provinces">
        // on peut utiliser deux vecteurs de string si on ne connait pas map
        map<string, string> CarCode_ProvincesCrossed;
        // accéder au premier élément <located> de theRiver
        XMLElement* currentLocated = theRiver->FirstChildElement("located");
        // pour tous les <located> les entrer dans la map
        while (currentLocated) {
            // nouvelle entrée dans la map
            CarCode_ProvincesCrossed.insert(pair<string, string>(currentLocated->Attribute("country"), currentLocated->Attribute("province")));
            // avancer
            currentLocated = currentLocated->NextSiblingElement("located");
        }
        // parcourir le vecteur theCountryCodes,
        // pour chaque pays : afficher son nom et afficher le nom des provinces traversées s'il y en a
        XMLElement* currentCountry;
        for (vector<string>::size_type i = 0; i != theCountryCodes.size(); i++) {
            currentCountry = getCountryXmlelementFromCode(theCountryCodes[i]);
            cout << "  - " << currentCountry->FirstChildElement("name")->GetText();
            // si le fleuve traverse des provinces du pays les afficher
            // vérifier d'abord que le fleuve traverse des provinces
            if (!CarCode_ProvincesCrossed.empty()) {
                // si currentCountry a des provinces traversées
                auto it = CarCode_ProvincesCrossed.find(theCountryCodes[i]);
                if (it != CarCode_ProvincesCrossed.end()) {
                    cout << ", où il traverse les divisions administratives suivantes : " << endl;
                    // le fleuve traverse une ou plusieurs provinces de currentCountry
                    vector<string> theProvinceCodes = split(it->second, ' ');
                    // parcourir le vecteur de provinces en les cherchant dans le pays currentCountry
                    for (vector<string>::size_type j = 0; j != theProvinceCodes.size(); j++) {
                        // comme on n'a pas d'ordre sur les provinces, on les parcours chaque fois depuis le début
                        // se positionner sur la première <province>
                        XMLElement* currentProvince = currentCountry->FirstChildElement("province");
                        // rechercher <province> sur l'attribut id qui vaut theProvinceCodes[j]
                        while (currentProvince && currentProvince->Attribute("id") != theProvinceCodes[j]) {
                            currentProvince = currentProvince->NextSiblingElement("province");
                        }
                        // si on a trouvé la province on l'affiche
                        if (currentProvince) {
                            cout << "      * " << currentProvince->FirstChildElement("name")->GetText() << endl;
                        } else {
                            // on n'a pas trouvé la province mais on aurait dû si les données étaient cohérentes
                            cout << "      * c'est étonnant la division administrative n'existe pas !" << endl;
                        }

                    }
                } else {
                    // le fleuve ne traverse pas de province dans le pays concerné, on saute une ligne
                    cout << endl;
                }
            }
        }
    } else {
        cout << "Le fleuve : " << riverName << ", n'existe pas !" << endl;
    }
}

/*
 * A COMPLETER
 */
void Mondial::printCityInformation(string cityName) const {
    //pour trouver une ville, pas d'autre choix
    // parcourir en séquence <country>
    //  pour chaque <country> parcourir en séquence les <province>
    //  s'il y a des <province>, pour chaque <province> parcourir les <city> à la recherche du <name> == cityName
    //  s'il n'y a pas de <province>, parcourir les <city> de <country> à la recherche du <name> == cityName
    
    bool trouve = false;
    XMLElement* currentCountry;
    XMLElement* currentProvince;
    XMLElement* currentCity;
    
    // accéder à <countriescategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* countriesCategory = racineMondial->FirstChildElement("countriescategory");
    // accéder au premier pays : premier fils <country> de <countriescategory>
    currentCountry = countriesCategory->FirstChildElement();
    // parcourir les <country>
    while ((currentCountry != nullptr) && !trouve) {
        // pour chaque <country> parcourir les <province>
        // accès à la première <province>
        currentProvince = currentCountry->FirstChildElement("province");
        // si le pays a des divisions administratives
        if (currentProvince) {
            // parcourir les <province>
            while ((currentProvince != nullptr) && !trouve) {
                // pour chaque <province> parcourir les <city>
                // accès à la première <city>
                currentCity = currentProvince->FirstChildElement("city");
                // parcourir les <city>
                while ((currentCity != nullptr) && !trouve) {
                    // on est maintenant sur <city>,regardons son nom
                    if (currentCity->FirstChildElement("name")->GetText() == cityName) {
                        trouve = true;
                    }
                    if (!trouve) {
                        // avancer au frère <city> suivant de currentCity
                        currentCity = currentCity->NextSiblingElement("city");
                    }
                    // si on a trouvé, on reste sur place pour avoir la city
                }
                if (!trouve) {
                    // avancer au frère <province> suivant de currentProvince
                    currentProvince = currentProvince->NextSiblingElement("province");
                }
                // si on a trouvé, on reste sur place pour avoir la province
            }
        } else {
            // le pays n'a pas de division administrative
            // parcourir les <city>
            // accès à la première <city>
            currentCity = currentCountry->FirstChildElement("city");
            // parcourir les <city>
            while ((currentCity != nullptr) && !trouve) {
                // on est maintenant sur <city>,regardons son nom
                if (currentCity->FirstChildElement("name")->GetText() == cityName) {
                    trouve = true;
                }
                if (!trouve) {
                    // avancer au frère <city> suivant de currentCity
                    currentCity = currentCity->NextSiblingElement("city");
                }
                // si on a trouvé, on reste sur place pour avoir la city
            }
        }
        if (!trouve) {
            // avancer au frère <country> suivant de currentCountry
            currentCountry = currentCountry->NextSiblingElement();
        }

        // si on a trouvé, on reste sur place pour avoir le pays
    }
    // (currentCountry == nullptr) || trouve)
    if (trouve) {
        // faire l'affichage des informations demandées
        cout << "La ville " << cityName << endl;
        cout << " - se trouve dans le pays : " << currentCountry->FirstChildElement("name")->GetText() << endl;
        if (currentProvince) {
            cout << " - dans la division adminstrative : " << currentProvince->FirstChildElement("name")->GetText() << endl;
        } // pas de province ne rien faire
        cout << " - sa latitude est : " << currentCity->FirstChildElement("latitude")->GetText() << endl;
        cout << " - sa longitude est : " << currentCity->FirstChildElement("longitude")->GetText() << endl;
        cout << " - son altitude est : " << currentCity->FirstChildElement("elevation")->GetText() << endl;
        cout << " - sa population est : " << currentCity->LastChildElement("population")->GetText() << endl;
    } else {
        cout << "La ville " << cityName << ", n'existe pas !";
    }
}

void Mondial::printIslandsInformations() const {
    // accéder à <islandscategory>, c’est un fils de l'élément <racineMondial>)
    XMLElement* islandsCategory = racineMondial->FirstChildElement("islandscategory");
    // accéder à la première île : premier fils <island> de <islandsCategory>
    XMLElement* currentIsland = islandsCategory->FirstChildElement();
    // parcourir les <island>
    while (currentIsland != nullptr) {
        cout << "***********************" << endl;
        // nom
        cout << currentIsland->FirstChildElement("name")->GetText() << endl;
        // pays (il peut y en avoir plusieurs)
        string theCountriesString = currentIsland->Attribute("country");
        //cout << "theCountriesString : " << theCountriesString << endl;
        // produire une liste de codes de pays dans un vecteur par exemple
        vector<string> theCountryCodes = split(theCountriesString, ' ');
        //cout << "theCountryCodes.size() : " << theCountryCodes.size();
        // produire une chaîne avec les noms de pays séparés par " * "
        string theFormatedCountriesString = "";
        XMLElement* theCountryXMLElement;
        int i = 0;
        // traitement jusqu'à l'avant dernier
        while (i < theCountryCodes.size()-1) {
            // récupérer le nom du pays
            theCountryXMLElement = getCountryXmlelementFromCode(theCountryCodes[i]);
            theFormatedCountriesString += theCountryXMLElement->FirstChildElement("name")->GetText();
            theFormatedCountriesString += " * ";
            i = i + 1;
        }
        // ajout du dernier
        theCountryXMLElement = getCountryXmlelementFromCode(theCountryCodes[i]);
        theFormatedCountriesString += theCountryXMLElement->FirstChildElement("name")->GetText();
        cout << theFormatedCountriesString << endl;
        if (currentIsland->FirstChildElement("area")) {
            cout << currentIsland->FirstChildElement("area")->GetText() << endl;
        } else {
            cout << 0 << endl;
        }
        if (currentIsland->FirstChildElement("latitude")) {
            cout << currentIsland->FirstChildElement("latitude")->GetText() << endl;
        } else {
            cout << 0 << endl;
        }
        if (currentIsland->FirstChildElement("longitude")) {
            cout << currentIsland->FirstChildElement("longitude")->GetText() << endl;
        } else {
            cout << 0 << endl;
        }
        if (currentIsland->FirstChildElement("elevation")) {
            cout << currentIsland->FirstChildElement("elevation")->GetText() << endl;
        } else {
            cout << 0 << endl;
        }
        
        // passer à l'île suivante (frère)
        currentIsland = currentIsland->NextSiblingElement();
    }
    
}

/*
 * Méthodes de service fournies
 */

template<typename Out>
void Mondial::split(string& s, char delim, Out result) const {
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        *(result++) = item;
    }
}

vector<std::string> Mondial::split(string& s, char delim) const {
    vector<std::string> elems;
    split(s, delim, back_inserter(elems));
    return elems;
}

Mondial::~Mondial() {
}