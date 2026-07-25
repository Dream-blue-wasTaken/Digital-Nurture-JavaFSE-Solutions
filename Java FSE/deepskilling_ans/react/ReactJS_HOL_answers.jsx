import React, { useState, useEffect, createContext, useContext } from 'react';
export const UserContext = createContext();
export function App() {
  const [user, setUser] = useState({ name: 'Jane Doe', role: 'Developer' });
  return (
    <UserContext.Provider value={{ user, setUser }}>
      <div>
        <Header title="React Hands-On Portal" />
        <CounterApp />
        <OnlineShopping />
        <TicketBookingApp />
        <TicketRaisingApp />
        <FetchUserApp />
      </div>
    </UserContext.Provider>
  );
}
export function Header({ title }) {
  return (
    <header style={{ padding: '1rem', background: '#333', color: '#fff' }}>
      <h1>{title}</h1>
    </header>
  );
}
export class OnlineShopping extends React.Component {
  render() {
    const items = [
      { id: 1, name: 'Laptop', price: 999 },
      { id: 2, name: 'Phone', price: 699 }
    ];
    return (
      <div>
        <h2>Online Shopping</h2>
        <Cart items={items} />
      </div>
    );
  }
}
export function Cart({ items }) {
  return (
    <ul>
      {items.map(item => (
        <li key={item.id}>
          {item.name} - ${item.price}
        </li>
      ))}
    </ul>
  );
}
export function CounterApp() {
  const [count, setCount] = useState(0);
  const updateEntry = () => setCount(prev => prev + 1);
  const updateExit = () => setCount(prev => (prev > 0 ? prev - 1 : 0));
  return (
    <div>
      <h2>People Counter</h2>
      <p>Count: {count}</p>
      <button onClick={updateEntry}>Enter</button>
      <button onClick={updateExit}>Exit</button>
    </div>
  );
}
export function TicketBookingApp() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  return (
    <div>
      <h2>Flight Ticket Booking</h2>
      {isLoggedIn ? (
        <div>
          <p>Welcome, User! Available Flights: FL-101, FL-202</p>
          <button onClick={() => setIsLoggedIn(false)}>Logout</button>
        </div>
      ) : (
        <div>
          <p>Please log in to view flight details.</p>
          <button onClick={() => setIsLoggedIn(true)}>Login</button>
        </div>
      )}
    </div>
  );
}
export function TicketRaisingApp() {
  const [ticket, setTicket] = useState({ issue: '', priority: 'Low' });
  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Ticket raised for: ${ticket.issue}`);
    setTicket({ issue: '', priority: 'Low' });
  };
  return (
    <form onSubmit={handleSubmit}>
      <h2>Raise Support Ticket</h2>
      <input
        type="text"
        value={ticket.issue}
        onChange={(e) => setTicket({ ...ticket, issue: e.target.value })}
        placeholder="Describe issue..."
        required
      />
      <select
        value={ticket.priority}
        onChange={(e) => setTicket({ ...ticket, priority: e.target.value })}
      >
        <option value="Low">Low</option>
        <option value="Medium">Medium</option>
        <option value="High">High</option>
      </select>
      <button type="submit">Submit</button>
    </form>
  );
}
export function FetchUserApp() {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    fetch('https://api.randomuser.me/')
      .then(res => res.json())
      .then(data => {
        setUserData(data.results[0]);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);
  if (loading) return <p>Loading user details...</p>;
  if (!userData) return <p>No user data available.</p>;
  return (
    <div>
      <h2>User Information</h2>
      <p>{userData.name.title} {userData.name.first} {userData.name.last}</p>
      <p>Email: {userData.email}</p>
    </div>
  );
}