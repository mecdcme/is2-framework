/**
 * Copyright 2019 ISTAT
 * <p>
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 * <p>
 * http://ec.europa.eu/idabc/eupl5
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 * @author Francesco Amato <framato @ istat.it>
 * @author Mauro Bruno <mbruno @ istat.it>
 * @author Paolo Francescangeli  <pafrance @ istat.it>
 * @author Renzo Iannacone <iannacone @ istat.it>
 * @author Stefano Macone <macone @ istat.it>
 * @version 1.0
 */
package it.istat.is2.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.istat.is2.app.util.IS2Const;
import it.istat.is2.commons.dto.LogCreateRequest;
import it.istat.is2.commons.dto.LogDTO;

@Service
public class LogService {
	
@Autowired
	protected RestTemplate restTemplate;
	// ​/log
	@Value("${services.log_url}")
	protected String logServiceUrl;


	public List<LogDTO> findAll() {
		ResponseEntity<LogDTO[]> response = this.restTemplate.getForEntity(logServiceUrl, LogDTO[].class);
		return Arrays.asList(response.getBody());

	}

	public List<LogDTO> findByIdSessione(Long idSession) {
		ResponseEntity<LogDTO[]> response = restTemplate.getForEntity(logServiceUrl + "/{idSession}", LogDTO[].class,
				idSession);
		return Arrays.asList(response.getBody());

	}

	public List<LogDTO> findByIdSessioneAndTipo(Long idSession, String idType) {
		ResponseEntity<LogDTO[]> response = restTemplate.getForEntity(logServiceUrl + "/{idSession}/{idType}",
				LogDTO[].class, idSession, idType);
		return Arrays.asList(response.getBody());
	}

	public void deleteByIdSessione(Long idSession) {

		restTemplate.delete(logServiceUrl + "/{idSession}", idSession);
	}

	public void deleteByIdSessioneAndTipo(Long idSession, String idType) {
		restTemplate.delete(logServiceUrl + "/{idSession}/{idType}", idSession, idType);
	}

	public void save(String msg, Long idSession) {

		save(msg, idSession, IS2Const.OUTPUT_DEFAULT);
	}

	public void save(String msg, Long idSession, String type) {
		LogCreateRequest log = new LogCreateRequest();
		log.setLogContent(msg);
		log.setType(type);
		log.setSessionId(idSession);
		restTemplate.postForObject(logServiceUrl, log, Boolean.class);
	}

}
