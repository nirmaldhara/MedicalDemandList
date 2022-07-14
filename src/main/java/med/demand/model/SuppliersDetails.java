package med.demand.model;

public class SuppliersDetails {
    private long id;
    private  String name;
    private String address;
    private String phoneNo;
    private float profitPercent;
    @Override
    public String toString() {
        return name;
    }
    public SuppliersDetails(long id,String name){
        this.id=id;
        this.name=name;
    }
    public SuppliersDetails(){

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getAddress() {
        return address;
    }

    public float getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(float profitPercent) {
        this.profitPercent = profitPercent;
    }


}
