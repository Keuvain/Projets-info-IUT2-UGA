#ifndef USAGER_H
#define USAGER_H

class Usager {
public:
    inline Usager(const std::string & nom, const std::string & groupe)
    : m_nom(nom), m_groupe(groupe) {
    }

    inline const std::string & getNom() const {
        return m_nom;
    }

    inline const std::string & getGroupe() const {
        return m_groupe;
    }
    virtual ~Usager() {}
private:
    std::string m_nom;
    std::string m_groupe;
};

#endif /* USAGER_H */

