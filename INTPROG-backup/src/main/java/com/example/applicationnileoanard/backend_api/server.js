const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');
 
const app = express();
const PORT = 5000;
 
// Middlewares
app.use(cors());
app.use(bodyParser.json());
 
// Root route handler
app.get('/', (req, res) => {
    res.json({
        message: 'Welcome to the API',
        endpoints: {
            register: 'POST /register',
            login: 'POST /login'
        }
    });
});

// Connect to MongoDB
mongoose.connect('mongodb+srv://noobdark38:syvzQknXCg7yvY7O@intprogdatabase.mugbqse.mongodb.net/?retryWrites=true&w=majority&appName=IntprogDatabase')
.then(() => console.log('MongoDB connected'))
.catch(err => console.error('MongoDB connection error:', err));
 
// Create User model
const UserSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        trim: true
    },
    email: {
        type: String,
        required: true,
        unique: true,
        trim: true,
        lowercase: true
    },
    password: {
        type: String,
        required: true
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
});
 
const User = mongoose.model('User', UserSchema);
 
// Register Endpoint
app.post('/register', async (req, res) => {
    try {
        const { username, email, password } = req.body;

        // Validate required fields
        if (!username || !email || !password) {
            return res.status(400).send({ message: 'All fields are required!' });
        }

        // Check if user already exists
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).send({ message: 'Email already registered!' });
        }

        // Create new user
        const user = new User({ username, email, password });
        await user.save();
        
        res.status(201).send({ 
            message: 'User registered successfully!',
            user: {
                username: user.username,
                email: user.email
            }
        });
    } catch (error) {
        console.error('Registration error:', error);
        res.status(500).send({ message: 'Error registering user' });
    }
});
 
// Login Endpoint
app.post('/login', async (req, res) => {
    try {
        const { email, password } = req.body;

        // Validate required fields
        if (!email || !password) {
            return res.status(400).send({ message: 'Email and password are required!' });
        }

        const user = await User.findOne({ email, password });
        if (user) {
            res.send({ 
                message: 'Login successful!',
                user: {
                    username: user.username,
                    email: user.email
                }
            });
        } else {
            res.status(400).send({ message: 'Invalid credentials' });
        }
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).send({ message: 'Error during login' });
    }
});
 
// Start Server
app.listen(PORT, '0.0.0.0', () => {
    console.log(`Server running on http://0.0.0.0:${PORT}`);
});