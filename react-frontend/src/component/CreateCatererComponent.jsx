import React, { Component } from 'react'
import CatererService from '../services/CatererService';



class CreateCatererComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            
                // step 2
                id: this.props.match.params.id,
                name: '',
                location:{
                    address: '',
                    cityName: '',
                    postalCode: ''
                },
                capacity:{
                    minGuestOccupancy: '',
                    maxGuestOccupancy: ''
                },
                contactInfo:{
                    phoneNumber: '',
                    mobileNumber: '',
                    emailAddress: ''
                }       
        }
        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.changeAddressHandler = this.changeAddressHandler.bind(this);
        this.changeCityNameHandler = this.changeCityNameHandler.bind(this);
        this.changeMinGuestOccupancyHandler = this.changeMinGuestOccupancyHandler.bind(this);
        this.changeMaxGuestOccupancyHandler = this.changeMaxGuestOccupancyHandler.bind(this);
        this.changePhoneNumberHandler = this.changePhoneNumberHandler.bind(this);
        this.changeMobileNumberHandler = this.changeMobileNumberHandler.bind(this);
        this.changeEmailIdHandler = this.changeEmailIdHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            CatererService.getCatererById(this.state.id).then( (res) =>{
                let caterer = res.data;
                this.setState({name: caterer.name,location:{
                    address: caterer.location.address,
                    cityName: caterer.location.cityName, postalCode: caterer.location.postalCode},capacity:{
                    minGuestOccupancy: caterer.capacity.minGuestOccupancy,
                    maxGuestOccupancy: caterer.capacity.maxGuestOccupancy},contactInfo:{
                    phoneNumber: caterer.contactInfo.phoneNumber,
                    mobileNumber: caterer.contactInfo.mobileNumber,
                    emailAddress: caterer.contactInfo.emailAddress
                }});
            });
        }        
    }
    saveOrUpdateCaterer = (e) => {
        e.preventDefault();
        let caterer = {name: this.state.name, location:{address: this.state.location.address, cityName: this.state.location.cityName,postalCode: this.state.location.postalCode}, capacity:{minGuestOccupancy: this.state.capacity.minGuestOccupancy,
            maxGuestOccupancy: this.state.capacity.maxGuestOccupancy}, contactInfo:{phoneNumber: this.state.contactInfo.phoneNumber, mobileNumber: this.state.contactInfo.mobileNumber,
            emailAddress: this.state.contactInfo.emailAddress}};
        console.log('caterer => ' + JSON.stringify(caterer));

        // step 5
        if(this.state.id === '_add'){
            CatererService.createCaterer(caterer).then(res =>{
                this.props.history.push('/caterers');
            });
        }else{
            CatererService.updateCaterer(caterer, this.state.id).then( res => {
                this.props.history.push('/caterers/');
            });
        }
    }
    
    changeNameHandler= (event) => {
        this.setState({name: event.target.value});
    }

    changeAddressHandler= (event) => {
        this.setState({location:{address: event.target.value,cityName: this.state.location.cityName,postalCode: this.state.location.postalCode}});
    }

    changeCityNameHandler= (event) => {
        this.setState({location:{address: this.state.location.address,cityName: event.target.value,postalCode: this.state.location.postalCode}});
    }


    changePostalCodeHandler= (event) => {
        this.setState({location:{address: this.state.location.address,cityName: this.state.location.cityName,postalCode: event.target.value}});
    }

    changeMinGuestOccupancyHandler= (event) => {
        this.setState({capacity:{minGuestOccupancy: event.target.value, maxGuestOccupancy: this.state.capacity.maxGuestOccupancy}});
    }

    changeMaxGuestOccupancyHandler= (event) => {
        this.setState({capacity:{minGuestOccupancy:this.state.capacity.minGuestOccupancy ,maxGuestOccupancy: event.target.value}});
    }

    changePhoneNumberHandler= (event) => {
        this.setState({contactInfo:{phoneNumber: event.target.value, mobileNumber: this.state.contactInfo.mobileNumber, emailAddress: this.state.contactInfo.emailAddress}});
    }

    changeMobileNumberHandler= (event) => {
        this.setState({contactInfo:{phoneNumber: this.state.contactInfo.phoneNumber, mobileNumber: event.target.value, emailAddress: this.state.contactInfo.emailAddress}});
    }

    changeEmailIdHandler= (event) => {
        this.setState({contactInfo:{phoneNumber: this.state.contactInfo.phoneNumber, mobileNumber: this.state.contactInfo.mobileNumber, emailAddress: event.target.value}});
    }

    cancel(){
        this.props.history.push('/caterers');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Caterer</h3>
        }else{
            return <h3 className="text-center">Update Caterer</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Name: </label>
                                            <input placeholder="Name" name="name" className="form-control"
                                                value={this.state.name} onChange={this.changeNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Address: </label>
                                            <input placeholder="Address" name="address" className="form-control" 
                                                value={this.state.location.address} onChange={this.changeAddressHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> City Name: </label>
                                            <input placeholder="CityName" name="cityName" className="form-control" 
                                                value={this.state.location.cityName} onChange={this.changeCityNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Postal code: </label>
                                            <input placeholder="PostalCode" name="postalCode" className="form-control" 
                                                value={this.state.location.postalCode} onChange={this.changePostalCodeHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Min Guests: </label>
                                            <input placeholder="MinGuests" name="minGuestOccupancy" className="form-control" 
                                                value={this.state.capacity.minGuestOccupancy} onChange={this.changeMinGuestOccupancyHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Max Guests: </label>
                                            <input placeholder="MaxGuests" name="maxGuestOccupancy" className="form-control" 
                                                value={this.state.capacity.maxGuestOccupancy} onChange={this.changeMaxGuestOccupancyHandler}/>
                                        </div>

                                        <div className = "form-group">
                                            <label> Phone Number: </label>
                                            <input placeholder="PhoneNumber" name="phoneNumber" className="form-control" 
                                                value={this.state.contactInfo.phoneNumber} onChange={this.changePhoneNumberHandler}/>
                                        </div>

                                        <div className = "form-group">
                                            <label> Mobile Number: </label>
                                            <input placeholder="MobileNumber" name="mobileNumber" className="form-control" 
                                                value={this.state.contactInfo.mobileNumber} onChange={this.changeMobileNumberHandler}/>
                                        </div>

                                        <div className = "form-group">
                                            <label> Email Id: </label>
                                            <input placeholder="EmailId" name="emailId" className="form-control" 
                                                value={this.state.contactInfo.emailAddress} onChange={this.changeEmailIdHandler}/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateCaterer}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateCatererComponent;