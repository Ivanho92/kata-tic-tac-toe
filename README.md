# KATA BNP - TicTacToe

This Restful API simulates a simple [TicTacToe](https://en.wikipedia.org/wiki/Tic-tac-toe) game between two players.

    Given this game board
    
    +----+----+----+
    | A1 | A2 | A3 |
    +----+----+----+
    | B1 | B2 | B3 |
    +----+----+----+
    | C1 | C2 | C3 |
    +----+----+----+
    
    And it's the turn of player X
    When it plays on cell B2
    Then the board is now
    
    +----+----+----+
    | A1 | A2 | A3 |
    +----+----+----+
    | B1 |  X | B3 |
    +----+----+----+
    | C1 | C2 | C3 |
    +----+----+----+
    
    And it's not the end of game.

Installation
------------

Install the project by running the following command on your terminal:

`git clone https://github.com/Ivanho92/kata-tic-tac-toe.git`

Once installed, you can run the project by running:

`./mvnw clean spring-boot:run`

The project should now be running on your localhost server on [http://localhost:8081](http://localhost:8081)

Endpoints
---------

Most endpoints return a JSON representing a game and its state, as follow:

    {
        "uuid": "fb010285-ba3f-4420-8a42-0e6178ca5058",
        "createdOn": 1670246187555,
        "updatedOn": 1670246418132,
        "playerX": "Julien",
        "playerO": "Ivan",
        "winner": null,
        "status": "ONGOING", // NEW, ONGOING, FINISHED
        "nextPlayer": "Ivan",
        "nextPlayerSymbol": "O",
        "outcome": null, // WIN, DRAW
        "board": {
            "fields": {
                "A2": "O",
                "C1": "X",
                "C3": null,
                "C2": null,
                "A1": "X",
                "A3": null,
                "B3": null,
                "B1": null,
                "B2": null
            }
        }
    }

### Get all games

`GET /api/games`

### Get a single game

`GET /api/games/{uuid}`

### Create a new game

`POST /api/games`

#### Request Body (application/json)

    {
        "playerX": "Julien",
        "playerO": "Ivan"
    }

### Play a move

`PUT /api/games/{uuid}/play`

#### Request Body (application/json)

    {
        "player": "Ivan",
        "field": "C1"
    }

### Delete a game

`DELETE /api/games/{uuid}`