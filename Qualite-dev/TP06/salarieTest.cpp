#include "gtest/gtest.h"
#include <map>
#include "Salarie.h"
#include "SalarieException.h"
using namespace std;

TEST(SalarieTest, Constructeur) {
    // Test noms incorrects lèvent NomIncorrectException
    for (const string &nom: {"", "Oss117", "James Bond", "-martin","martin-"}) {
        EXPECT_THROW(Salarie s(nom), NomIncorrectException)
                            << "Const. avec nom=\"" << nom << "\" lève NomIncorrectException";
    }
    // Test noms corrects ne lèvent pas d'exception
    for (const string &nom: {"martin", "Duran-duran"}) {
        EXPECT_NO_THROW(Salarie s(nom))
                            << "Const. avec nom=\"" << nom << "\" ne lève pas d'exception";
    }
    // Test numéroSS invalides lèvent NumeroIncorrectException
    for (const string &numeroSS: {"12345", "12345678901234", "123456789012A"}) {
        EXPECT_THROW(Salarie s("Toto", numeroSS), NumeroIncorrectException)
                            << "Const. avec numéroSS=\"" << numeroSS << "\" lève NumeroIncorrectException";
    }
    // Test numéroSS à 13 chiffres ne commençant pas par 1 ou 2 lèvent NumeroIncorrectException
    for (const string & i: {"0", "3", "4", "5", "6", "7", "8", "9"}) {
        EXPECT_THROW(Salarie s("Toto", i + "234567890123"), NumeroIncorrectException)
                            << "Const. numéroSS débutant par " << i << " lève NumeroIncorrectException";
    }
    // Test numéros valides ne lèvent pas d'exception
    EXPECT_NO_THROW(Salarie s("Toto", "1234567890123"))
                        << "Const. numéroSS débutant par 1 ne lève pas d'exception";
    EXPECT_NO_THROW(Salarie s("Toto", "2234567890123"))
                        << "Const. numéroSS débutant par 2 ne lève pas d'exception";
    // Test salaires incorrect lèvent SalaireIncorrectException
    EXPECT_THROW(Salarie s("Toto", "1234567890123", Salarie::SMIC - 0.1f), SalaireIncorrectException)
                        << "Const. salaire trop petit lève SalaireIncorrectException";
    EXPECT_THROW(Salarie s("Toto", "1234567890123", Salarie::SMIC * Salarie::COEFF_SMIC + 0.1f),
                 SalaireIncorrectException)
                        << "Const. salaire trop grand lève SalaireIncorrectException";
    // Test salaires corrects ne lèvent pas d'exception
    EXPECT_NO_THROW(Salarie s("Toto", "1234567890123", Salarie::SMIC))
                        << "Const. salaire min ne lève pas d'exception";
    EXPECT_NO_THROW(Salarie s("Toto", "1234567890123", Salarie::SMIC * Salarie::COEFF_SMIC))
                        << "Const. salaire max ne lève pas d'exception";
    EXPECT_NO_THROW(Salarie s("Toto", "1234567890123",
                            (Salarie::SMIC + Salarie::SMIC * Salarie::COEFF_SMIC) / 2.0f))
                        << "Const. salaire median ne lève pas d'exception";
    // test construction bien réalisée
    string nom = "MARTIN", numeroSS = "1234567890123";
    float salaire = Salarie::SMIC;
    Salarie s(nom, numeroSS, salaire);
    ASSERT_EQ(nom, s.getNom()) << "Const. Nom ok";
    ASSERT_EQ(numeroSS, s.getNumeroSS()) << "Const. Numero SS ok";
    ASSERT_FLOAT_EQ(salaire, s.getSalaireMensuel()) << "Const. Salaire ok";
}

TEST(SalarieTest, GetImpot) {
    // On teste TOUTES les tranches d'imposition possibles
    for (auto it = Salarie::TRANCHES_IMPOT.begin();
         it != Salarie::TRANCHES_IMPOT.end(); it++) {

        // On récupère les informations de la tranche courante
        float revenuAnnuel = it->first;
        float tauxCourant = it->second;
        float revenuMensuel = revenuAnnuel / 12.0f;

        // Cas 1. On teste le cas du revenu inférieur à la  limite de la tranche courante
        try {
            float revenuMensuelInf = revenuMensuel - 1.0f;
            float revenuAnnuelInf = revenuMensuelInf * 12.0f;
            Salarie unSalarie("M", "1234567890123", revenuMensuelInf);
            // On construit un message spécifique pour ce test
            stringstream message; // stringstream : chaine manipulable comme un flux
            message << "Revenu < " << revenuAnnuel << " => taux = " << tauxCourant;
            EXPECT_FLOAT_EQ(unSalarie.getImpot(), revenuAnnuelInf * tauxCourant) << message.str();
        } catch (SalaireIncorrectException &e) {
            // pas d'assertion si salaire pas dans l'intervalle autorisé
        }

        // Cas 2. On teste le cas du revenu égal à la limite de la tranche courante
        auto itSuivant = it;
        itSuivant++; // On va chercher la tranche suivante si elle existe
        if (itSuivant != Salarie::TRANCHES_IMPOT.end()) {
            try {
                float tauxSuivant = itSuivant->second;
                Salarie unSalarie("M", "1234567890123", revenuMensuel);
                // On construit un message spécifique pour ce test
                stringstream message;
                message << "Revenu = " << revenuAnnuel << " => taux = " << tauxSuivant;
                EXPECT_FLOAT_EQ(unSalarie.getImpot(), revenuAnnuel * tauxSuivant) << message.str();
            } catch (SalaireIncorrectException &e) {
                // pas d'assertion si salaire pas dans l'intervalle autorisé
            }
        }
    }
}

TEST(SalarieTest, GetNom) {
    // Test noms bien transformés en majuscules
    for (auto &it: map<const string, const string>{{"martin",      "MARTIN"},
                                                   {"MARTIN",      "MARTIN"},
                                                   {"duran-duran", "DURAN-DURAN"},
                                                   {"DURAN-DURAN", "DURAN-DURAN"}}) {
        Salarie s(it.first);
        EXPECT_EQ(s.getNom(), it.second)
                            << "Salarie(\"" << it.first << "\").getNom() doit renvoyer " << it.second << "\"";
    }
}

TEST(SalarieTest, SetNom) {
    Salarie s;
    // Test setter avec noms incorrects
    for (const string &nom: {"", "Oss117", "James Bond", "-martin"}) {
        EXPECT_THROW(s.setNom(nom), NomIncorrectException)
                            << "salarie.setNom(\"" << nom << "\") lève NomIncorrectException";
    }
    // Test setter avec noms corrects
    for (const string &nom: {"martin", "Duran-duran"}) {
        EXPECT_NO_THROW(s.setNom(nom))
                            << "salarie.setNom(\"" << nom << "\") ne lève pas d'exception";
    }
    // Test setter modifie bien le nom
    for (auto &it: map<const string, const string>{{"martin",      "MARTIN"},
                                                   {"MARTIN",      "MARTIN"},
                                                   {"duran-duran", "DURAN-DURAN"},
                                                   {"DURAN-DURAN", "DURAN-DURAN"}}) {
        s.setNom(it.first);
        EXPECT_EQ(s.getNom(), it.second)
                            << "salarie.setNom(\"" << it.first << "\") a modifié le nom en " << it.second << "\"";
    }
}

TEST(SalarieTest, SetNumeroSS) {
    Salarie s;
    // Test numéroSS invalides
    for (const string &numeroSS: {"12345", "12345678901234", "123456789012A"}) {
        EXPECT_THROW(s.setNumeroSS(numeroSS), NumeroIncorrectException)
                            << "salarie.setNumeroSS(\"" << numeroSS << "\") lève NumeroIncorrectException";
    }
    // Test numéroSS à 13 chiffres ne commençant pas par 1 ou 2
    for (const string & i: {"0", "3", "4", "5", "6", "7", "8", "9"}) {
        EXPECT_THROW(s.setNumeroSS(i + "234567890123"), NumeroIncorrectException)
                            << "salarie.setNumeroSS avec numéro débutant par " << i << "234567890123"
                            << " lève NumeroIncorrectException";
    }
    // Test numéros valides
    EXPECT_NO_THROW(s.setNumeroSS("1234567890123"))
                        << "salarie.setNumeroSS avec numéro débutant par 1 ne lève pas d'exception";
    EXPECT_NO_THROW(s.setNumeroSS("2234567890123"))
                        << "salarie.setNumeroSS avec numéro débutant par 2 ne lève pas d'exception";
    // Test setter fait bien la modification
    const string numValide = "2013456789012";
    s.setNumeroSS(numValide);
    EXPECT_EQ(s.getNumeroSS(), "2013456789012") << "salarie.setNumeroSS modifie bien le numéro de SS";
}

TEST(SalarieTest, SetSalaire) {
    Salarie s;
    // Test salaires invalides
    EXPECT_THROW(s.setSalaireMensuel(Salarie::SMIC - 0.1f), SalaireIncorrectException)
                        << "Set salaire trop petit lève SalaireIncorrectException";
    EXPECT_THROW(s.setSalaireMensuel(Salarie::SMIC * Salarie::COEFF_SMIC + 0.1f),
                 SalaireIncorrectException)
                        << "Set salaire trop grand lève SalaireIncorrectException";
    // Test salaires valides
    EXPECT_NO_THROW(s.setSalaireMensuel(Salarie::SMIC))
                        << "Set salaire min ne lève pas d'exception";
    EXPECT_NO_THROW(s.setSalaireMensuel(Salarie::SMIC * Salarie::COEFF_SMIC))
                        << "Set salaire max ne lève pas d'exception";
    EXPECT_NO_THROW(s.setSalaireMensuel((Salarie::SMIC + Salarie::SMIC * Salarie::COEFF_SMIC) / 2.0f))
                        << "Set salaire median ne lève pas d'exception";
    // Test setter modifie bien le salaire
    const float newSalaire = 2345.67f;
    s.setSalaireMensuel(newSalaire);
    EXPECT_FLOAT_EQ(s.getSalaireMensuel(),newSalaire) << "setSalaire modifie correctement le salaire";
}