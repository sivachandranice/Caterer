import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListCatererComponent from './component/ListCatererComponent';
import HeaderComponent from './component/HeaderComponent';
import FooterComponent from './component/FooterComponent';
import CreateCatererComponent from './component/CreateCatererComponent';


function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListCatererComponent}></Route>
                          <Route path = "/caterers" component = {ListCatererComponent}></Route>
                          <Route path = "/add-caterer/:id" component = {CreateCatererComponent}></Route>
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;