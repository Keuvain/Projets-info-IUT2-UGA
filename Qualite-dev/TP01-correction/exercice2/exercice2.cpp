#include <cstdlib>
#include <iostream>
#include "EntierContraint.h"
using namespace std;

int main(int argc, char** argv) {
    
    EntierContraint test1;
    
    // TP1 - Exercice 2
    
    // Juste deux exemples de tests
    // 1. Tester qu'on ne peut pas construire un EntierContraint avec val < min < max
    cout << "-- Test constructeur avec val < min leve exception" << endl;
    try {
        EntierContraint ec(0, 1, 1000); // Allocation automatique d'un EntierContraint ec
        cout << "Echec : exception non levee" << endl;
    } catch (char const * exception) {
        cout << "Succes : exception levee : " << exception << endl;
    }
    // 2. Tester qu'on peut construire sans exception un EntierContraint avec min < val < max 
    cout << "-- Test constructeur avec val < min < max ne leve pas d'exception" << endl;
    try {
        EntierContraint ec(10, 1, 1000);
        cout << "Succes : exception non levee" << endl;
    } catch (char const * exception) {
        cout << "Echec : exception non levee" << exception << endl;
    }
    
    // ... Il faudrait tester tous les cas : val=min, val=max, val>max, min<max ...

    return 0;
}

