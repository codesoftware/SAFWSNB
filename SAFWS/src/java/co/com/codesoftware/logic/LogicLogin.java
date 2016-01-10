package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;

public class LogicLogin {
	/**
	 * Funcion encargada de realizar el llamado a la funcion de autenticacion y
	 * mediante su resultado poder hacer las validaciones pertinentes
	 * 
	 * @param name
	 * @param pass
	 * @return
	 */
	public List<String> login(String name, String pass) {
		List<String> response = new ArrayList<String>();
		try (ReadFunction rf = new ReadFunction()) {
			rf.setNombreFuncion("US_FAUTENTICAR_USUA");
			rf.setNumParam(2);
			rf.addParametro(name, DataType.TEXT);
			rf.addParametro(pass, DataType.TEXT);
			rf.callFunctionJdbc();
			response = rf.getRespuestaPg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
