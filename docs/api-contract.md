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

⚠️ **Format réel confirmé le 2026-08 : texte brut, PAS du JSON.**

```
Content-Type: text/plain;charset=UTF-8

eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOi...   ← le token JWT directement, sans accolades JSON
```

Le mobile utilise donc `Response<String>` (converter Scalars), pas un DTO JSON.

### Réponse échec

- Code retourné à confirmer avec Yessin (401 supposé pour identifiants invalides)
- Format du corps d'erreur à confirmer (texte brut aussi, ou JSON avec message ?)

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

⚠️ À documenter — structure exacte de `RegisterRequest.kt` côté backend
(quels champs, lesquels sont obligatoires).

### Réponse

⚠️ À documenter — même format que login (texte brut) ou différent ?

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
