# HSTONEAGE

## Prerequisites

To build and execute code you will need to have git and maven installed on your computer.

## Building and usage

```sh
$ git clone https://github.com/olszowski-daniel/hstoneage.git
$ cd hstoneage
$ mvn spring-boot:run
```
## REST Api
### Posting
Post a message
```sh
POST /{userId}/posts (200 OK)
```
Request body:
```sh
Content-Type: application/json
```
```json
{
    "message":"MESSAGE_CONTENT"
}
```
### Following
Follow another user
```sh
POST /{userId}/follow/{toBeFollowedUserId} (200 OK)
```
### Wall
Get users' own messages
```sh
GET /{userId}/posts (200 OK)
GET /{userId}/wall (200 OK)
```
### Timeline
Get messages created by followed users
```sh
GET /{userId}/timeline (200 OK)
```
