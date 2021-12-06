import React, { Component } from 'react';
import CatererService from '../services/CatererService';

class ListCatererComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                caterers: []
        }
        this.addCaterer = this.addCaterer.bind(this);
        this.editCaterer = this.editCaterer.bind(this);
        this.deleteCaterer = this.deleteCaterer.bind(this);
    }

    deleteCaterer(id){
        CatererService.deleteCaterer(id).then( res => {
            this.setState({caterers: this.state.caterers.filter(caterer => caterer.id !== id)});
        });
    }

    
    editCaterer(id){
        this.props.history.push(`/add-caterer/${id}`);
    }

    componentDidMount(){
        CatererService.getCaterers().then((res) => {
            this.setState({ caterers: res.data});
        });
    }

    addCaterer(){
        this.props.history.push('/add-caterer/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Caterers List</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addCaterer}> Add Caterer</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Name</th>
                                    <th> Address</th>
                                    <th> City</th>
                                    <th> Postal Code</th>
                                    <th> Min Guests</th>
                                    <th> Max Guests</th>
                                    <th> Phone Number</th>
                                    <th> Mobile Number</th>
                                    <th> e-mail id</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.caterers.map(
                                        caterer => 
                                        <tr key = {caterer.id}>
                                             <td> {caterer.name} </td>   
                                             <td> {caterer.location.address}</td>
                                             <td> {caterer.location.cityName}</td>
                                             <td> {caterer.location.postalCode}</td>
                                             <td> {caterer.capacity.minGuestOccupancy}</td>
                                             <td> {caterer.capacity.maxGuestOccupancy}</td>
                                             <td> {caterer.contactInfo.phoneNumber}</td>
                                             <td> {caterer.contactInfo.mobileNumber}</td>
                                             <td> {caterer.contactInfo.emailAddress}</td>
                                             <td>
                                                 <button onClick={ () => this.editCaterer(caterer.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteCaterer(caterer.id)} className="btn btn-danger">Delete </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    } 
}

export default ListCatererComponent;

