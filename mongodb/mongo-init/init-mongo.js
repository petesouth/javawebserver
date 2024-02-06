db = db.getSiblingDB('mydatabase');

// Create players collection and insert players
db.players.insertMany([
    { _id: 1, firstname: "John", lastname: "Doe", email: "john@example.com", created: new Date(), updated: new Date() },
    { _id: 2, firstname: "Jane", lastname: "Doe", email: "jane@example.com", created: new Date(), updated: new Date() },
    { _id: 3, firstname: "Jim", lastname: "Beam", email: "jim@example.com", created: new Date(), updated: new Date() },
    { _id: 4, firstname: "Jack", lastname: "Daniels", email: "jack@example.com", created: new Date(), updated: new Date() }
]);

// Create tickets collection and insert tickets
db.tickets.insertMany([
    { _id: 1, playerId: 1, created: new Date(), updated: new Date() },
    { _id: 2, playerId: 1, created: new Date(), updated: new Date() },
    { _id: 3, playerId: 2, created: new Date(), updated: new Date() },
    { _id: 4, playerId: 2, created: new Date(), updated: new Date() },
    { _id: 5, playerId: 3, created: new Date(), updated: new Date() },
    { _id: 6, playerId: 4, created: new Date(), updated: new Date() },
    // ... add more tickets for other players ...
]);

// Create phones collection and insert phone numbers
db.phones.insertMany([
    { _id: 1, playerId: 1, phonenumber: "123-456-7891", created: new Date(), updated: new Date() },
    { _id: 2, playerId: 2, phonenumber: "123-456-7892", created: new Date(), updated: new Date() },
    { _id: 3, playerId: 3, phonenumber: "123-456-7893", created: new Date(), updated: new Date() },
    { _id: 4, playerId: 4, phonenumber: "123-456-7894", created: new Date(), updated: new Date() },
    // ... add more phone numbers for other players ...
]);
