# Beeldi – Android Technical Test

This project is an implementation of the Beeldi technical test.  
It focuses on code quality, Clean Architecture, modern Android standards and a simple, maintainable structure.

## 🚀 Tech Stack
- **Kotlin**
- **Jetpack Compose** (UI)
- **Coroutines + Flow**
- **StateFlow** (UI state management)
- **Clean Architecture** (data / domain / presentation)
- **Manual DI** (kept simple for the test)
- **JUnit + Mockito**
- **Ktlint** for code style checks

---

## ✅ Progress & Requirements

- [x] Cloner le repository de test et pousser les modifications sur un repo GitHub perso
- [x] Corriger les erreurs de compilation initiales
- [x] Mettre en place une structure inspirée de la Clean Architecture (data / domain / presentation)
- [x] Modifier le datasource pour qu'il retourne un `Flow<List<...>>`
- [x] Connecter le Flow au ViewModel via un `UseCase` et un `Repository`
- [ ] Afficher la liste des équipements avec Jetpack Compose
- [ ] Au clic sur un équipement, afficher une fiche détail d’équipement
- [ ] Ajouter un filtre par `UserRole` (ADMIN / MAINTAINER / AUDITOR)
- [ ] Intégrer Ktlint pour la qualité de code
- [ ] Ajouter Mockito et des tests unitaires (UseCases / Repository / ViewModel)
- [ ] Faire un dernier passage de refacto / nettoyage (naming, organisation, commentaires)

## Screen examples :

Equipment list : 

<img width="743" height="1175" alt="image" src="https://github.com/user-attachments/assets/0c13af0e-5a03-4d18-9a23-6f055e77370f" />

Equipment details :

<img width="743" height="1171" alt="image" src="https://github.com/user-attachments/assets/86ab3ad1-bd3a-4486-9262-4ce459c8fcf1" />
