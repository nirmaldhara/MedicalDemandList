/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.enums;

/**
 *
 * @author ndhara
 */
public enum Message {
    	DATA_PARSING_FAIL("Data Parsing Fail!!"),
		NOT_VALID_DATA("Not a valid data"),
        NO_DATA("No data found"),
        CAN_NOT_PRINT("Can not print the receipt"),
		SETTING_SAVE_SUCCESS("Setting details saved successfully"),
	    LIST_NAME("DEMAND_LIST"),
		SUPPLIES_ADDED("Suppliers Added Successfully"),
		SUPPLIES_ADD_ERR("Can not add  Suppliers"),
		MEDICINE_ADD_ERR("Can not add  Medicine"),
		SUPPLIES_EDIT("Suppliers Edited Successfully"),
		SUPPLIES_EDIT_ERROR("Can not edit suppliers"),
		SUPPLIES_DELETE("Suppliers Deleted successfully"),
		MEDICINE_DELETE("Medicine Deleted successfully"),
		MEDICINE_ADDED("Medicine Added successfully"),
		MEDICINE_EDIT("Medicine Edited successfully"),
		DEMAND_LIST_SAVE("Demand List Saved Successfully")
	;


	private final String origin;

	private Message(String origin) {
		this.origin = origin;
	}

	public String get() {
		return origin;
	}

}
