const express = require('express');
const app = express();
const PORT = 8080;

// Simple text route
app.get('/', (req, res) => {
  res.send('Welcome to the Express Demo!');
});

// JSON API route
app.get('/api/data', (req, res) => {
  res.json({ message: "Hello!", status: "success" });
});

app.listen(PORT, () => {
  console.log(`Server started at port ${PORT}`);
});