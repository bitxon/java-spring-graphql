# GraphQL with Spring Boot

## Run application

Run application with test context (auto start postgress)

```shell
./gradlew bootTestRun
```

Note: if you want to run the application from your IDE\
Use `RunLocalApplication`

## Test application

Open [GraphiQL UI](http://localhost:8080/graphiql)

<details>
    <summary>Get Employee By Id</summary>

```graphql
query GetEmployeeById($id: ID!) {
    employee(id: $id) {
        id
        name
        email
    }
}
```
```json
{"id": "1"}
```
</details>

<details>
    <summary>Get All Employees</summary>

```graphql
query GetAllEmployees {
    employees {
        id
        name
        email
    }
}
```
</details>

<details>
    <summary>Get Organization By Id</summary>

```graphql
query GetOrganizationById($id: ID!) {
    organization(id: $id) {
        id
        name
    }
}
```
```json
{"id": "1"}
```
</details>

<details>
    <summary>Get All Organizations With Pagination</summary>

```graphql
query GetAllOrganizations($first: Int, $after: String, $last: Int, $before: String){
    organizations(first: $first, after: $after, last: $last, before: $before) {
        pageInfo {
            hasNextPage
            endCursor
        }
        edges {
            node {
                id
                name
            }
        }
    }
}
```
```json
{"first": 5}
```
</details>

<br>

<details>
    <summary>Create Employee</summary>

```graphql
mutation CreateEmployee($name: String!, $email: String!, $organizationId: Int!) {
    createEmployee(employee: { name: $name, email: $email, organizationId: $organizationId}) {
        id
        name
        email
    }
}
```
```json
{
  "name": "John Doe",
  "email": "john.doe@email.com",
  "organizationId": 3
}
```
</details>

<details>
    <summary>Create Organization</summary>

```graphql
mutation CreateOrganization($name: String!) {
    createOrganization(name: $name) {
        id
        name
    }
}
```
```json
{"name": "Organization Test Name"}
```
</details>
