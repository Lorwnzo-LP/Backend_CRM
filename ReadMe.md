## CRM para a Vasta Imoveis



## 🔐 Auth

POST /auth/login

    Body:
    {
        "email": "string",
        "password": "string"
    }
    Response:
    {
        "success": "boolean",
        "token": "string",
        "message": "string"
    }

GET /auth/me

    Response:
    {
        "success": "boolean",
        "data":{
            "id": "UUID",
            "nome": String,
            "email": String,
            "role": RoleUsers
            "regiao": RegiaoUsers
        },
        "message": "string"
    }

---

## 👤 Users

POST /users (Somente gerente)

    Body:
    {
    "name": "string",
    "telefone":"string",
    "password": "string",
    "email": "string",
    "role": "string" (opcional)
    "regiao": "string" (opcional)
    }
    
    Response:
    {
        "success": "boolean",
        "data":{
            "id": "UUID",
            "name": "string",
            "telefone":"string",
            "email": "string",
        },
        "message": "string"
    }

GET /users (Somente gerente)

    response:
        "success": "boolean",
        "data": {
            "id": "UUID",
            "nome": "string",
            "email": "string",
            "telefone": "string",
            "role": "RoleUsers",
            "regiao":"RegiaoUsers"
        }
        "message": "string"

---

## 📊 Leads

GET /leads (autenticado)


    response:
        "success": "boolean",
        "data":{
            "id": "UUID",
            "user_id": "UUID",
            "nome": "string",
            "email": "string",
            "telefone": "string",
        },
        "message": "string"

POST /leads

    body:
        "nome": "string",
        "telefone": "string",
        "email": "string"

---

## 📊 LeadNotes
POST /leadNotes

    body:
        "note": "string"
        "leadId": "UUID",

GET /leadNotes/{id}

    response:
        "id": "UUID",


(falta outras chamadas API)