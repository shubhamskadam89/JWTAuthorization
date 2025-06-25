import './App.css';
import Login from './Login.js';

function App() {
  return (
    <div className="App bg-gray-100 min-h-screen flex flex-col justify-center items-center px-4">
      <h1 className="text-3xl font-bold text-gray-700 mb-6 text-center">
        Spring Security JWT Auth
      </h1>
      <Login />
    </div>
  );
}

export default App;
