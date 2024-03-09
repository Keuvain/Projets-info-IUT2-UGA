#include <cstdlib>
#include <iostream>
#include "EntierContraint.h"
using namespace std;

int main(int argc, char** argv) {

    // TP2 - Exercice 1 - Surcharge d'opérateurs
    EntierContraint ec(1);
    int i;
    
    // Test operator int
    i = int(ec); // appel de EntierContraint::operator int() const
    cout << "Test cast 1 - i=" << i << " (doit être 1)" << endl;

    ec.setVal(2);
    i = (int) ec; // autre syntaxe équivalente
    cout << "Test cast 2 - i=" << i << " (doit être 2)" << endl;

    ec.setVal(3);
    i = ec; // traduit automatiquement par le compilateur en i=int(ec);
    cout << "Test cast 3 - i=" << i << " (doit être 3)" << endl;

    // Test operator >>
    cout << "Test >> - Entrez la valeur 4 SVP : ";
    cin >> ec; // appel de operator >> (istream & entree, EntierContraint & ec)
    cout << "Test >> - ec=" << ec.getVal() << " (doit être 4)" << endl;

    // Test operator <<
    ec.setVal(5); // appel de operator << (ostream & sortie, const EntierContraint & ec)
    cout << "Test << - ec=" << ec << " (doit être 5)" << endl;

    // Test Affectation
    i = 10;
    ec = i; 
    cout << "Test affectation - ec=" << ec << " (doit être 10)" << endl;

    return 0;
}

