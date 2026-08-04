# Contrat API — ERP Mobile

Document vivant. À mettre à jour à chaque fois qu'un endpoint est ajouté ou modifié
côté backend. Le mobile se base UNIQUEMENT sur ce document (pas de suppositions).

Base URL locale (dev) : `http://localhost:8080` (backend) /
`http://10.0.2.2:8080` (vu depuis l'émulateur Android)

---

## POST /api/auth/login

**Publique** (pas de token requis)

### Requête

```json
{
  "email": "user@example.com",
  "password": "motdepasse"
}
```

### Réponse succès — 200

⚠️ **Format réel confirmé : texte brut, PAS du JSON.**

```
Content-Type: text/plain;charset=UTF-8

eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOi...   ← le token JWT directement, sans accolades JSON
```

Le mobile utilise donc `Response<String>` (converter Scalars), pas un DTO JSON.

### Réponse échec — 401 Unauthorized ✅ corrigé le 2026-08-05

Avant correction, le backend renvoyait 200 avec un texte d'erreur — ce qui empêchait
le mobile de distinguer succès/échec via le code HTTP. Corrigé dans `AuthController.kt`
en passant le retour de `login()` à `ResponseEntity<String>`.

```
Content-Type: text/plain;charset=UTF-8
Status: 401

"User not found"        ← email inexistant
"Invalid password"      ← mot de passe incorrect
```

Le mobile affiche dans les deux cas : "Email ou mot de passe incorrect"
(pas de distinction volontaire côté UI, pour ne pas révéler quel champ est faux —
bonne pratique de sécurité).

### Contenu du token JWT (décodé)

```json
{
  "sub": "email@example.com",
  "role": "ADMIN",
  "iat": 1785762039,
  "exp": 1785798039
}
```

---

## POST /api/auth/register

**Publique** (pas de token requis)

### Requête

```json
{
  "email": "user@example.com",
  "password": "motdepasse",
  "role": "ADMIN"
}
```

⚠️ À confirmer avec Yessin : les valeurs exactes possibles pour `role`
(ADMIN, COMMERCIAL, MAGASINIER... ?) et si le champ est obligatoire ou a une valeur par défaut.

### Réponse succès — 200

Renvoie actuellement l'objet `User` complet en JSON, **incluant le mot de passe chiffré (BCrypt)** :

```json
{
  "id": 1,
  "email": "user@example.com",
  "password": "$2a$10$...",
  "role": "ADMIN"
}
```

⚠️ **À corriger côté backend** : ne pas renvoyer le champ `password`, même chiffré.
Prévoir un DTO de réponse dédié (`UserResponse` sans le mot de passe) — planifié étape 3
de la roadmap actuelle.

### Réponse échec

⚠️ À documenter — comportement non testé si l'email existe déjà (devrait renvoyer une erreur,
à vérifier qu'il ne crée pas un doublon ou n'écrase pas silencieusement).

---

## GET /api/hello

**Publique** — route de test, aucun paramètre.

### Réponse — 200

```
Hello from the ERP backend!
```

---

## Routes métier (articles, commandes, clients)

Pas encore implémentées côté backend. À documenter ici au fur et à mesure
qu'elles sont créées.

---

## Template à copier pour un nouvel endpoint

```markdown
## METHODE /api/chemin

**Publique** ou **Protégée** (token requis)

### Requête
\`\`\`json
{ }
\`\`\`

### Réponse succès — CODE
\`\`\`json
{ }
\`\`\`

### Réponse échec
- Code : 
- Corps : 
```
