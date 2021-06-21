package uk.gov.companieshouse.personapi.model;
public class Address {
    private String houseNum;
    private String street;
    private String town;
    private  String county;
    private String postcode;
    private String country;
    public Address(){}

    public Address( String houseNum, String street, String town, String county, String postcode, String country){
        this.houseNum = houseNum;
        this.street = street;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
        this.country = country;


    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "model.Address" +
                "houseNum='" + houseNum + '\'' +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'';
    }
}
