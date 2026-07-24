// ============================================================
// React Hands-on Exercises 9-13
// ============================================================

// ============================================================
// HANDS-ON 9: Cricket App - ES6 Features (map, arrow, destructuring)
// ============================================================

/*
// npx create-react-app cricketapp
// cd cricketapp
*/

// src/Components/ListofPlayers.js
import React from 'react';

function ListofPlayers() {
  // Array of 11 players with names and scores
  const players = [
    { name: 'Virat Kohli', score: 85 },
    { name: 'Rohit Sharma', score: 65 },
    { name: 'Jasprit Bumrah', score: 12 },
    { name: 'Ravindra Jadeja', score: 45 },
    { name: 'KL Rahul', score: 92 },
    { name: 'Shikhar Dhawan', score: 55 },
    { name: 'Rishabh Pant', score: 35 },
    { name: 'Hardik Pandya', score: 78 },
    { name: 'Mohammed Shami', score: 8 },
    { name: 'Yuzvendra Chahal', score: 15 },
    { name: 'Cheteshwar Pujara', score: 3 }
  ];

  // Using map() to display players
  // Using arrow functions to filter players with score below 70
  const lowScoringPlayers = players.filter(player => player.score < 70);

  return (
    <div>
      <h2>All Players</h2>
      <ul>
        {players.map((player, index) => (
          <li key={index}>
            {player.name} - {player.score} runs
          </li>
        ))}
      </ul>

      <h2>Players with Score Below 70 (Filtered using Arrow Function)</h2>
      <ul>
        {lowScoringPlayers.map((player, index) => (
          <li key={index} style={{ color: 'red' }}>
            {player.name} - {player.score} runs
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ListofPlayers;

// src/Components/IndianPlayers.js
import React from 'react';

function IndianPlayers() {
  // Using destructuring
  const T20players = ['Virat Kohli', 'Rohit Sharma', 'KL Rahul', 'Rishabh Pant', 'Hardik Pandya'];
  const RanjiTrophy = ['Cheteshwar Pujara', 'Ajinkya Rahane', 'Ravindra Jadeja'];
  
  // Destructuring assignment
  const [player1, player2, ...rest] = T20players;
  
  // Merging arrays using spread operator
  const allPlayers = [...T20players, ...RanjiTrophy];
  
  // Using array destructuring for odd/even
  const [oddTeam1, evenTeam1, oddTeam2, evenTeam2, oddTeam3] = T20players;

  return (
    <div>
      <h2>Indian Cricket Team</h2>
      
      <h3>Using Destructuring</h3>
      <p>First T20 Player: {player1}</p>
      <p>Second T20 Player: {player2}</p>
      <p>Rest of T20 Players: {rest.join(', ')}</p>

      <h3>Odd Team Players</h3>
      <p>{oddTeam1}, {oddTeam2}, {oddTeam3}</p>

      <h3>Even Team Players</h3>
      <p>{evenTeam1}, {evenTeam2}</p>

      <h3>Merged Squad (T20 + Ranji Trophy)</h3>
      <ul>
        {allPlayers.map((player, index) => (
          <li key={index}>{player}</li>
        ))}
      </ul>
    </div>
  );
}

export default IndianPlayers;

// src/App.js (Updated)
/*
import React from 'react';
import ListofPlayers from './Components/ListofPlayers';
import IndianPlayers from './Components/IndianPlayers';

function App() {
  const showIndianPlayers = true; // Flag variable for if-else conditional

  return (
    <div className="App">
      <h1>Cricket App</h1>
      
      {showIndianPlayers ? (
        <IndianPlayers />
      ) : (
        <ListofPlayers />
      )}
    </div>
  );
}

export default App;
*/

// ============================================================
// HANDS-ON 10: Office Space Rental App (JSX)
// ============================================================

/*
// npx create-react-app officespacerentalapp
// cd officespacerentalapp
*/

// src/App.js
/*
import React from 'react';
import './App.css';

function App() {
  // Office object with details
  const office = {
    name: 'Tech Hub - Co-working Space',
    rent: 55000,
    address: 'Bangalore, Karnataka'
  };

  // List of office objects
  const offices = [
    { id: 1, name: 'WeWork - MG Road', rent: 45000, address: 'MG Road, Bangalore' },
    { id: 2, name: 'Regus - Koramangala', rent: 65000, address: 'Koramangala, Bangalore' },
    { id: 3, name: 'Cowork - HSR Layout', rent: 35000, address: 'HSR Layout, Bangalore' },
    { id: 4, name: 'Incubex - Indiranagar', rent: 75000, address: 'Indiranagar, Bangalore' },
    { id: 5, name: '91Springboard - JP Nagar', rent: 25000, address: 'JP Nagar, Bangalore' }
  ];

  return (
    <div className="App">
      <h1>Office Space Rental</h1>
      
      {/* Display office image using attribute */}
      <img 
        src="https://images.unsplash.com/photo-1497366216548-37526070297c?w=600" 
        alt="Office Space" 
        width="400"
      />

      {/* Single office object */}
      <div className="office-detail">
        <h2>Featured Office</h2>
        <p><strong>Name:</strong> {office.name}</p>
        <p><strong>Rent:</strong> 
          <span style={{ color: office.rent < 60000 ? 'red' : 'green', fontWeight: 'bold' }}>
            ₹{office.rent.toLocaleString()}
          </span>
        </p>
        <p><strong>Address:</strong> {office.address}</p>
      </div>

      {/* List of offices - looping through items */}
      <h2>Available Offices</h2>
      <div className="office-list">
        {offices.map(office => (
          <div key={office.id} className="office-card">
            <h3>{office.name}</h3>
            <p><strong>Rent:</strong> 
              <span style={{ color: office.rent < 60000 ? 'red' : 'green', fontWeight: 'bold' }}>
                ₹{office.rent.toLocaleString()}
              </span>
            </p>
            <p><strong>Address:</strong> {office.address}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
*/

// src/App.css
/*
.App {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  color: #333;
}

.office-detail {
  margin: 20px 0;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f5f5f5;
}

.office-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 15px;
}

.office-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
*/

// ============================================================
// HANDS-ON 11: Event Handling Examples (eventexamplesapp)
// ============================================================

/*
// npx create-react-app eventexamplesapp
// cd eventexamplesapp
*/

// src/Components/Counter.js
/*
import React from 'react';

class Counter extends React.Component {
  constructor(props) {
    super(props);
    this.state = { count: 0 };
  }

  // Task 2: Increment and display message
  increment = () => {
    this.setState(prevState => ({ count: prevState.count + 1 }));
    this.sayHello();
  }

  decrement = () => {
    this.setState(prevState => ({ count: prevState.count - 1 }));
  }

  sayHello = () => {
    alert('Say Hello');
  }

  // Task 3: Function with argument
  sayWelcome = (message) => {
    alert(message);
  }

  // Task 4: Synthetic event
  handleOnPress = () => {
    alert('I was clicked');
  }

  render() {
    return (
      <div>
        <h2>Counter: {this.state.count}</h2>
        <button onClick={this.increment}>Increment</button>
        <button onClick={this.decrement}>Decrement</button>
        <br /><br />
        <button onClick={() => this.sayWelcome('welcome')}>Say Welcome</button>
        <br /><br />
        <button onClick={this.handleOnPress}>OnPress</button>
      </div>
    );
  }
}

export default Counter;
*/

// src/Components/CurrencyConverter.js
/*
import React from 'react';

class CurrencyConverter extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rupees: 0,
      euros: 0
    };
  }

  handleSubmit = (e) => {
    e.preventDefault();
    // Conversion rate: 1 INR = 0.011 EUR (example)
    const euros = this.state.rupees * 0.011;
    this.setState({ euros: euros.toFixed(2) });
  }

  handleChange = (e) => {
    this.setState({ rupees: e.target.value, euros: 0 });
  }

  render() {
    return (
      <div>
        <h2>Currency Converter</h2>
        <form onSubmit={this.handleSubmit}>
          <label>
            Indian Rupees (INR):
            <input 
              type="number" 
              value={this.state.rupees}
              onChange={this.handleChange}
              placeholder="Enter amount in INR"
            />
          </label>
          <button type="submit">Convert to Euro</button>
        </form>
        {this.state.euros > 0 && (
          <h3>€ {this.state.euros} EUR</h3>
        )}
      </div>
    );
  }
}

export default CurrencyConverter;
*/

// ============================================================
// HANDS-ON 12: Ticket Booking App (ticketbookingapp)
// ============================================================

/*
// npx create-react-app ticketbookingapp
// cd ticketbookingapp
*/

// src/App.js
/*
import React, { useState } from 'react';

// Flight details
const flights = [
  { id: 1, from: 'Mumbai', to: 'Delhi', time: '06:00', price: 4500 },
  { id: 2, from: 'Delhi', to: 'Bangalore', time: '08:30', price: 5200 },
  { id: 3, from: 'Bangalore', to: 'Chennai', time: '10:00', price: 3800 },
  { id: 4, from: 'Mumbai', to: 'Goa', time: '14:00', price: 3200 },
];

// Guest Page - Browse flights only
function GuestPage() {
  return (
    <div>
      <h2>Browse Flights (Guest User)</h2>
      <p>Please login to book tickets.</p>
      {flights.map(flight => (
        <div key={flight.id} className="flight-card">
          <h3>{flight.from} → {flight.to}</h3>
          <p>Departure: {flight.time}</p>
          <p>Price: ₹{flight.price}</p>
          <button disabled>Login to Book</button>
        </div>
      ))}
    </div>
  );
}

// User Page - Can book tickets
function UserPage() {
  return (
    <div>
      <h2>Book Tickets (Logged In)</h2>
      {flights.map(flight => (
        <div key={flight.id} className="flight-card">
          <h3>{flight.from} → {flight.to}</h3>
          <p>Departure: {flight.time}</p>
          <p>Price: ₹{flight.price}</p>
          <button onClick={() => alert(`Booking ${flight.from} to ${flight.to}`)}>
            Book Now
          </button>
        </div>
      ))}
    </div>
  );
}

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <div className="App">
      <h1>Flight Ticket Booking</h1>
      
      <div className="auth-buttons">
        {isLoggedIn ? (
          <button onClick={() => setIsLoggedIn(false)}>Logout</button>
        ) : (
          <button onClick={() => setIsLoggedIn(true)}>Login</button>
        )}
      </div>

      {/* Conditional rendering based on login status */}
      {isLoggedIn ? <UserPage /> : <GuestPage />}
    </div>
  );
}

export default App;
*/

// ============================================================
// HANDS-ON 13: Blogger App (bloggerapp) - Conditional Rendering
// ============================================================

/*
// npx create-react-app bloggerapp
// cd bloggerapp
*/

// src/App.js
/*
import React, { useState } from 'react';

// Component 1: Book Details
function BookDetails() {
  const books = [
    { title: 'The Great Gatsby', author: 'F. Scott Fitzgerald', rating: 4.5 },
    { title: '1984', author: 'George Orwell', rating: 4.7 },
    { title: 'To Kill a Mockingbird', author: 'Harper Lee', rating: 4.8 }
  ];

  return (
    <div>
      <h2>Book Details</h2>
      {books.length > 0 ? (
        <ul>
          {books.map((book, index) => (
            <li key={index}>
              <strong>{book.title}</strong> by {book.author} 
              <span style={{ color: book.rating >= 4.5 ? 'green' : 'orange' }}>
                {' '}({book.rating}/5)
              </span>
            </li>
          ))}
        </ul>
      ) : (
        <p>No books available</p>
      )}
    </div>
  );
}

// Component 2: Blog Details
function BlogDetails() {
  const [showLatest, setShowLatest] = useState(true);
  const blogs = [
    { title: 'Getting Started with React', date: '2026-06-15', author: 'John' },
    { title: 'Spring Boot Best Practices', date: '2026-06-10', author: 'Jane' },
    { title: 'Microservices Architecture', date: '2026-06-05', author: 'Bob' }
  ];

  return (
    <div>
      <h2>Blog Details</h2>
      <button onClick={() => setShowLatest(!showLatest)}>
        {showLatest ? 'Show All Blogs' : 'Show Latest'}
      </button>
      
      {showLatest ? (
        <div>
          <h3>Latest Blog</h3>
          <p><strong>{blogs[0].title}</strong></p>
          <p>By {blogs[0].author} on {blogs[0].date}</p>
        </div>
      ) : (
        <ul>
          {blogs.map((blog, index) => (
            <li key={index}>
              <strong>{blog.title}</strong> - {blog.author} ({blog.date})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

// Component 3: Course Details
function CourseDetails() {
  const [selectedCourse, setSelectedCourse] = useState(null);
  const courses = [
    { id: 1, name: 'Java Full Stack', duration: '6 months', fee: 50000 },
    { id: 2, name: 'React Development', duration: '3 months', fee: 30000 },
    { id: 3, name: 'Spring Boot', duration: '2 months', fee: 25000 }
  ];

  return (
    <div>
      <h2>Course Details</h2>
      
      {/* Element variable approach */}
      {courses.map(course => (
        <div key={course.id}>
          <h3>{course.name}</h3>
          <p>Duration: {course.duration} | Fee: ₹{course.fee}</p>
          <button onClick={() => setSelectedCourse(course)}>View Details</button>
          {selectedCourse?.id === course.id && (
            <div style={{ backgroundColor: '#e3f2fd', padding: '10px', margin: '10px 0' }}>
              <p>Enroll now for {course.name}!</p>
              <p>Special discount: 10% off if enrolled this week.</p>
            </div>
          )}
        </div>
      ))}

      {/* Logical && approach - another way of conditional rendering */}
      {selectedCourse && (
        <div className="selected-course">
          <h3>Selected Course: {selectedCourse.name}</h3>
          <p>Duration: {selectedCourse.duration}</p>
          <p>Fee: ₹{selectedCourse.fee}</p>
        </div>
      )}
    </div>
  );
}

// Main App
function App() {
  const [activeSection, setActiveSection] = useState('books');
  const showHeader = true;

  return (
    <div className="App">
      <h1>Blogger App</h1>
      
      {/* Switch/Conditional rendering with element variables */}
      {showHeader && <p className="welcome">Welcome to the Blogger App!</p>}

      <div className="nav-buttons">
        <button onClick={() => setActiveSection('books')}>Books</button>
        <button onClick={() => setActiveSection('blogs')}>Blogs</button>
        <button onClick={() => setActiveSection('courses')}>Courses</button>
      </div>

      {/* Conditional rendering with if-else equivalent using ternary */}
      {(() => {
        switch (activeSection) {
          case 'books': return <BookDetails />;
          case 'blogs': return <BlogDetails />;
          case 'courses': return <CourseDetails />;
          default: return <BookDetails />;
        }
      })()}
    </div>
  );
}

export default App;
*/
