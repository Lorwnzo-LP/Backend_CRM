## 🔐 Auth

POST /auth/login

Body:
{
"email": "string",
"password": "string"
}

---

## 👤 Users

POST /users

---

## 📊 Leads

GET /leads (autenticado)

    Rotas API até o momento:
        User:
            POST /users
            PUT /users/{id}
        Leads:
            POST /leads
            GET /leads
            GET /leads/{id}
            PUT /leads/{id}
        auth:
            POST /auth/login


        Forma de usar:
            - Crie o usuário
            - Receber token
            - realizar as ações enviando "Bearer token" no header
     