import './App.css';
import Card from "./components/card/Card";

import Header from "./components/header/Header";
import SearchBar from "./components/searchBar/SearchBar";

function App() {
    return <>
        <main className='container'>
            <Header/>
            <SearchBar/>
            <div className='board'>
                <Card/>
                <Card/>
                <Card/>
                <Card/>
                <Card/>
                <Card/>
                <Card/>
                <Card/>
            </div>
        </main>
    </>;
}

export default App
