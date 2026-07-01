# 🚀 AWS Lambda IAM Admin API (Spring-style Java Backend)

This project is a **serverless IAM Admin API** built using:

- ✅ Java (AWS SDK v2)
- ✅ AWS Lambda (Java runtime)
- ✅ API Gateway (REST API with proxy integration)
- ✅ Spring-style architecture (without Spring Boot web)
- ✅ Custom Router Framework (like Spring MVC)

---

# 🧠 📌 Project Overview

This application provides **REST APIs to manage AWS IAM users and policies**, deployed in a **serverless architecture**.

It implements a clean backend layered design:

```
API Gateway → Lambda → Router → Handler → Controller → Service → AWS IAM
```

---

# 📦 ✅ Features

### ✅ IAM Operations

- 📄 List all users  
- 📄 List user policies  
- 📄 List access keys  
- ➕ Create new IAM user  
- 🔗 Attach policy to user  

---

# 🏗️ ✅ Architecture

```
┌──────────────────────────────────────────────────────────────────────────┐
│                         REQUEST FLOW DIAGRAM                             │
└──────────────────────────────────────────────────────────────────────────┘

                    📬 Client (Postman / Curl)
                              │
                              │ HTTPS Request
                              ▼
                  ┌─────────────────────────┐
                  │   API Gateway REST API  │
                  │  (Routing /users, etc.) │
                  └────────────┬────────────┘
                               │
                               │ Lambda Proxy Integration
                               ▼
                  ┌─────────────────────────┐
                  │  AWS Lambda (ApiHandler)│
                  │  Entry point for app    │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │   LambdaRouter          │
                  │  (Custom Routing Engine)│
                  │  - Matches method+path  │
                  │  - Extracts parameters  │
                  └────────────┬────────────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
    ┌──────────────────┐ ┌──────────────┐ ┌──────────────────┐
    │ ListUsersHandler │ │CreateUserH.  │ │AttachPolicyH.    │
    │ (GET /users)     │ │(POST /users) │ │(POST /users/.../p)
    └────────┬─────────┘ └──────┬───────┘ └────────┬─────────┘
             │                   │                  │
             └───────────────────┼──────────────────┘
                                 │
                                 ▼
                  ┌─────────────────────────────┐
                  │   IAMController             │
                  │  Business Logic Coordinator │
                  │  - listUsers()              │
                  │  - createUser()             │
                  │  - attachPolicyToUser()     │
                  │  - listUserPolicies()       │
                  │  - listAccessKeys()         │
                  └────────────┬────────────────┘
                               │
                               ▼
                  ┌─────────────────────────────┐
                  │   IAMService                │
                  │  (AWS SDK v2 Operations)    │
                  │  - IamClient calls          │
                  │  - Data transformation      │
                  │  - Response mapping         │
                  └────────────┬────────────────┘
                               │
                               ▼
                  ┌─────────────────────────────┐
                  │   AWS IAM Service           │
                  │  - ListUsers                │
                  │  - CreateUser               │
                  │  - AttachUserPolicy         │
                  │  - ListUserPolicies         │
                  │  - ListAccessKeys           │
                  └─────────────────────────────┘
                               │
                               │ IAM Response
                               ▼
         ┌─────────────────────────────────────────┐
         │  Response flows back through layers:    │
         │  IAMService → IAMController → Handler   │
         │        → LambdaRouter → API Gateway     │
         │              → Client (JSON response)   │
         └─────────────────────────────────────────┘


LAYER BREAKDOWN:
═══════════════════════════════════════════════════

🌐 API LAYER
   └─ API Gateway: REST API endpoint routing

⚡ LAMBDA LAYER  
   └─ ApiHandler: AWS Lambda handler implementation

🔀 ROUTING LAYER
   └─ LambdaRouter: Custom route matching engine
   
🎯 HANDLER LAYER (Per-endpoint handlers)
   ├─ ListUsersHandler: Handles GET /users
   ├─ CreateUserHandler: Handles POST /users/{userName}
   ├─ AttachPolicyHandler: Handles POST /users/{userName}/policy
   ├─ ListUserPoliciesHandler: Handles GET /users/{userName}/policy
   └─ ListAccessKeysHandler: Handles GET /users/{userName}/keys

🎮 CONTROLLER LAYER
   └─ IAMController: Orchestrates business operations

⚙️ SERVICE LAYER
   └─ IAMService: AWS SDK v2 integration & data mapping

☁️ AWS LAYER
   └─ AWS IAM: Actual AWS service
```

---

# ⚙️ ✅ Technologies Used

- Java 17
- AWS SDK v2 (IAMClient)
- AWS Lambda
- API Gateway (REST API)
- Spring Context (Dependency Injection)
- Jackson (JSON processing)
- Maven (Build tool)

---

# 🧩 ✅ API Endpoints

---

## ✅ 1. List Users

```
GET /users
```

### ✅ Response

```json
[
  {
    "userName": "rahulshivsharan",
    "userId": "AIDXXXX",
    "arn": "arn:aws:iam::123:user/rahulshivsharan"
  }
]
```

***

## ✅ 2. Create User

```
POST /users/{userName}
```

### ✅ Example

```
POST /users/test-user
```

***

## ✅ 3. Attach Policy to User

```
POST /users/{userName}/policy
```

### ✅ Body

```json
{
  "policyArn": "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}
```

***

## ✅ 4. List User Policies

```
GET /users/{userName}/policy
```

***

## ✅ 5. List User Access Keys

```
GET /users/{userName}/keys
```

***

# 🧱 ✅ Project Structure

```
src/main/java/com/sp/ws/lambda
│
├── handler              → Lambda request handlers (per-endpoint)
│   ├── ApiHandler.java  → Main Lambda handler entry point
│   ├── ListUsersHandler.java
│   ├── CreateUserHandler.java
│   ├── AttachPolicyHandler.java
│   ├── ListUserPoliciesHandler.java
│   ├── ListAccessKeysHandler.java
│   └── RouteHandler.java (interface)
│
├── router               → Custom routing engine
│   ├── LambdaRouter.java → Route matching & dispatching
│   └── Route.java → Route definition & pattern matching
│
├── controller           → Business logic coordination
│   └── IAMController.java → Orchestrates IAM operations
│
├── service              → AWS SDK integration
│   └── IAMService.java → IAM API calls & data mapping
│
├── dto                  → Data Transfer Objects
│   ├── IAMUserDTO.java
│   ├── IAMPolicyDTO.java
│   ├── IAMAccessKeyDTO.java
│   └── AttachPolicyResponseDTO.java
│
├── mapper               → Data mapping utilities
│   └── [Mapper classes]
│
└── config               → Spring bean configuration
    └── AppConfig.java → Application context setup
```

***

# 🚀 ✅ Deployment Steps

***

## ✅ Step 1: Build JAR

```bash
mvn clean package
```

Output:

```
target/awsLambdaIAMSpringBoot02-0.0.1.jar
```

***

## ✅ Step 2: Create Lambda

1. Go to AWS Lambda
2. Click → Create Function
3. Select:
   * Runtime → Java 17
4. Click → Create Function

***

## ✅ Step 3: Upload JAR

1. Go to Code tab
2. Upload `.jar` file

***

## ✅ Step 4: Configure Handler

```
com.sp.ws.lambda.handler.ApiHandler
```

***

## ✅ Step 5: Add IAM Permissions

Go to IAM → Policies → Create Policy

```json
{
  "Effect": "Allow",
  "Action": [
    "iam:ListUsers",
    "iam:CreateUser",
    "iam:AttachUserPolicy",
    "iam:ListUserPolicies",
    "iam:ListAccessKeys"
  ],
  "Resource": "*"
}
```

Attach it to the Lambda execution role.

***

# 🌐 ✅ API Gateway Setup

***

## ✅ Step 6: Create REST API

* Select → REST API (NOT HTTP API)

***

## ✅ Step 7: Create Resources

```
/users
/users/{userName}
/users/{userName}/policy
/users/{userName}/keys
```

***

## ✅ Step 8: Add Methods

For each resource:

* GET /users
* POST /users/{userName}
* POST /users/{userName}/policy
* GET /users/{userName}/policy
* GET /users/{userName}/keys

***

## ✅ Step 9: Configure Integration

* Integration type → Lambda
* ✅ Enable **Lambda Proxy Integration**

***

## ✅ Step 10: Deploy API

Create Stage:

```
iamLambdaSpringBootDeploy
```

***

## ✅ Final URL

```
https://{api-id}.execute-api.us-east-1.amazonaws.com/iamLambdaSpringBootDeploy
```

***

# 📬 ✅ Testing

***

## ✅ List Users

```bash
curl GET /users
```

***

## ✅ Create User

```bash
curl -X POST /users/test-user
```

***

## ✅ Attach Policy

```bash
curl -X POST /users/test-user/policy \
-H "Content-Type: application/json" \
-d '{ "policyArn": "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess" }'
```

***

# ⚠️ ✅ Common Issues & Fixes

***

## ❌ 500 Internal Server Error

✅ Fix:

* Check CloudWatch logs
* Ensure all beans are wired properly

***

## ❌ "Route not found"

✅ Fix:

* Correct API Gateway path
* Normalize stage prefix

***

## ❌ "Missing Authentication Token"

✅ Fix:

* Route not created in API Gateway
* Deploy API again

***

## ❌ 403 IAM Error

✅ Fix:

* Add IAM permissions to Lambda role

***

## ❌ Path is null

✅ Fix:

* Enable Lambda Proxy Integration
* Use REST API (not HTTP API)

***

# 🧠 ✅ Key Concepts Learned

* Serverless architecture (API Gateway + Lambda)
* IAM role-based authentication
* Custom routing engine (like Spring MVC)
* Path variable handling
* Dependency Injection using Spring Context
* Layered architecture design (Handler → Controller → Service)

***

# 🚀 ✅ Future Improvements

* ✅ Add Swagger documentation
* ✅ Add validation layer
* ✅ Add logging (CloudWatch structured logs)
* ✅ Add authentication (JWT / API Key)
* ✅ Add pagination

***

# 👨‍💻 ✅ Author

**Rahul Shivsharan**

***

# ⭐ ✅ Final Note

This project demonstrates:

✅ Real-world cloud backend engineering  
✅ Clean architecture design  
✅ AWS production concepts

Feel free to fork, enhance, and contribute 🚀
