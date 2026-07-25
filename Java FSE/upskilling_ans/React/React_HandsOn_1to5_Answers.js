// ============================================================
// React Hands-on Exercises 1-5
// ============================================================

// ============================================================
// HANDS-ON 1: Create a new React Application "myfirstreact"
// ============================================================

/*
// Step 1: Install Create-react-app globally
// npx create-react-app myfirstreact

// Step 2: Navigate to the project folder
// cd myfirstreact

// Step 3: Replace src/App.js with:
*/

// src/App.js
import React from 'react';

function App() {
  return (
    <div>
      <h1>welcome to the first session of React</h1>
    </div>
  );
}

export default App;

// ============================================================
// HANDS-ON 2: Student Management Portal (StudentApp)
// ============================================================

/*
// npx create-react-app StudentApp
// cd StudentApp
// mkdir src/Components
*/

// src/Components/Home.js
import React from 'react';

const Home = () => {
  return (
    <div>
      <h1>Welcome to the Home Page</h1>
      <p>This is the Student Management Portal Home page.</p>
    </div>
  );
};

export default Home;

// src/Components/About.js
import React from 'react';

const About = () => {
  return (
    <div>
      <h1>About Us</h1>
      <p>We provide quality education and student management services.</p>
    </div>
  );
};

export default About;

// src/Components/Contact.js
import React from 'react';

const Contact = () => {
  return (
    <div>
      <h1>Contact Us</h1>
      <p>Email: contact@studentportal.com</p>
      <p>Phone: +1-234-567-8900</p>
    </div>
  );
};

export default Contact;

// src/App.js (Updated with components)
/*
import React from 'react';
import Home from './Components/Home';
import About from './Components/About';
import Contact from './Components/Contact';

function App() {
  return (
    <div>
      <Home />
      <About />
      <Contact />
    </div>
  );
}

export default App;
*/

// ============================================================
// HANDS-ON 3: Score Calculator App (scorecalculatorapp)
// ============================================================

/*
// npx create-react-app scorecalculatorapp
// cd scorecalculatorapp
// mkdir src/Components
// mkdir src/Stylesheets
*/

// src/Components/CalculateScore.js
import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore(props) {
  const { Name, School, Total, Goal } = props;
  const average = Total / 4; // Assuming 4 subjects
  
  return (
    <div className="score-card">
      <h2>Student Score Card</h2>
      <table>
        <tbody>
          <tr>
            <td>Student Name:</td>
            <td>{Name}</td>
          </tr>
          <tr>
            <td>School:</td>
            <td>{School}</td>
          </tr>
          <tr>
            <td>Total Marks:</td>
            <td>{Total}</td>
          </tr>
          <tr>
            <td>Average:</td>
            <td>{average.toFixed(2)}</td>
          </tr>
          <tr>
            <td>Goal:</td>
            <td>{Goal}</td>
          </tr>
          <tr>
            <td>Status:</td>
            <td className={average >= Goal ? 'pass' : 'fail'}>
              {average >= Goal ? 'Achieved' : 'Not Achieved'}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default CalculateScore;

// src/Stylesheets/mystyle.css
/*
.score-card {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  font-family: Arial, sans-serif;
}

.score-card h2 {
  text-align: center;
  color: #333;
}

.score-card table {
  width: 100%;
  border-collapse: collapse;
}

.score-card td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

.score-card td:first-child {
  font-weight: bold;
  width: 120px;
}

.pass {
  color: green;
  font-weight: bold;
}

.fail {
  color: red;
  font-weight: bold;
}
*/

// src/App.js (Updated)
/*
import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div className="App">
      <CalculateScore 
        Name="John Doe"
        School="ABC High School"
        Total={380}
        Goal={75}
      />
    </div>
  );
}

export default App;
*/

// ============================================================
// HANDS-ON 4: Blog App with Lifecycle Hooks (blogapp)
// ============================================================

/*
// npx create-react-app blogapp
// cd blogapp
*/

// src/Components/Posts.js
import React from 'react';

class Posts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      posts: [],
      hasError: false
    };
  }

  // componentDidMount lifecycle hook
  componentDidMount() {
    this.loadPosts();
  }

  // componentDidCatch lifecycle hook for error handling
  componentDidCatch(error, info) {
    this.setState({ hasError: true });
    console.error('Error caught:', error, info);
  }

  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then(response => response.json())
      .then(data => {
        this.setState({ posts: data.slice(0, 10) }); // First 10 posts
      })
      .catch(error => {
        console.error('Error fetching posts:', error);
        this.setState({ hasError: true });
      });
  }

  render() {
    if (this.state.hasError) {
      return <h2>Something went wrong. Please try again later.</h2>;
    }

    return (
      <div>
        <h1>Blog Posts</h1>
        {this.state.posts.map(post => (
          <div key={post.id} className="post">
            <h3>{post.title}</h3>
            <p>{post.body}</p>
          </div>
        ))}
      </div>
    );
  }
}

export default Posts;

// src/App.js (Updated)
/*
import React from 'react';
import Posts from './Components/Posts';

function App() {
  return (
    <div className="App">
      <Posts />
    </div>
  );
}

export default App;
*/

// ============================================================
// HANDS-ON 5: Styling React Components (Academy Dashboard)
// ============================================================

/*
// The app has CohortDetails component styled with CSS Modules
*/

// src/Components/CohortDetails.module.css
/*
.box {
  width: 300px;
  display: inline-block;
  margin: 10px;
  padding: 10px 20px;
  border: 1px solid black;
  border-radius: 10px;
}

.box dt {
  font-weight: 500;
}
*/

// src/Components/CohortDetails.js
/*
import React from 'react';
import styles from './CohortDetails.module.css';

function CohortDetails({ name, status, startDate, endDate, members }) {
  const headingStyle = {
    color: status === 'ongoing' ? 'green' : 'blue'
  };

  return (
    <div className={styles.box}>
      <h3 style={headingStyle}>{name}</h3>
      <dl>
        <dt>Status</dt>
        <dd>{status}</dd>
        <dt>Start Date</dt>
        <dd>{startDate}</dd>
        <dt>End Date</dt>
        <dd>{endDate}</dd>
        <dt>Members</dt>
        <dd>{members}</dd>
      </dl>
    </div>
  );
}

export default CohortDetails;
*/

// src/App.js (Updated)
/*
import React from 'react';
import CohortDetails from './Components/CohortDetails';

function App() {
  const cohorts = [
    { name: 'Java FSE Batch 1', status: 'ongoing', startDate: '2026-01-15', endDate: '2026-07-15', members: 25 },
    { name: 'React Batch 2', status: 'completed', startDate: '2025-09-01', endDate: '2026-01-30', members: 20 },
  ];

  return (
    <div className="App">
      <h1>My Academy Dashboard</h1>
      {cohorts.map((cohort, index) => (
        <CohortDetails key={index} {...cohort} />
      ))}
    </div>
  );
}

export default App;
*/
