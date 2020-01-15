package model;

import exceptions.InvalidFormatException;


public class VendingID implements java.io.Serializable {

	private static final long serialVersionUID = 1123581321L;
	public enum State {AL, AK, AR, AZ, CA, CO, CT, DC, DE, FL, GA, HI,
		IA, ID, IL, IN, KS, KY, LA, ME, MD, MA, MI, MN, MS, MO, MT, NE,
		NV, NH, NJ, NM, NY, NC, ND, OH, OK, OR, PA, RI, SC, SD, TN, TX,
		UT, VT, VA, WA, WI, WV, WY};
	private String id;
	private State state;
	private String city;
	private String address;
	private int zip;
	private String location;

	
	public VendingID(String id, State state, String city, int zip,
			String address, String location) throws InvalidFormatException {
		this.id = id;
		this.setState(state);
		this.setCity(city);
		this.setZip(zip);
		this.setAddress(address);
		this.setLocation(location);
	}

	public String getId() {
		return id;
	}

	public State getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}
	
	public int getZip() {
		return zip;
	}
	
	public String getLocation() {
		return location;
	}

	protected void setState(State state) {
		this.state = state;
	}

	protected void setCity(String city) {
		this.city = city;
	}

	protected void setAddress(String address) {
		this.address = address;
	}

	protected void setZip(int zip) throws InvalidFormatException {
		if ((zip + "").length() != 5) {
			throw new InvalidFormatException("Zip length length != 5: " + zip);
		}
		this.zip = zip;
	}
	
	protected void setLocation(String location) {
		this.location = location;
	}
}
