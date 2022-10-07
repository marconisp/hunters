package br.com.jhisolution.user.hunters.service.impl;

//import com.google.gson.Gson;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.report.util.ReportExporter;
import br.com.jhisolution.user.hunters.repository.PagarRepository;
import br.com.jhisolution.user.hunters.service.PagarService;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.PagarDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

/**
 * Service Implementation for managing {@link Pagar}.
 */
@Service
@Transactional
public class PagarServiceImpl implements PagarService {

    private final Logger log = LoggerFactory.getLogger(PagarServiceImpl.class);

    private final PagarRepository pagarRepository;
    private final Path fileStorageLocation;

    public PagarServiceImpl(PagarRepository pagarRepository) {
        this.pagarRepository = pagarRepository;
        this.fileStorageLocation = Paths.get("./").toAbsolutePath().normalize();
    }

    @Override
    public Pagar save(Pagar pagar) {
        log.debug("Request to save Pagar : {}", pagar);
        return pagarRepository.save(pagar);
    }

    @Override
    public Pagar update(Pagar pagar) {
        log.debug("Request to save Pagar : {}", pagar);
        return pagarRepository.save(pagar);
    }

    @Override
    public Optional<Pagar> partialUpdate(Pagar pagar) {
        log.debug("Request to partially update Pagar : {}", pagar);

        return pagarRepository
            .findById(pagar.getId())
            .map(existingPagar -> {
                if (pagar.getData() != null) {
                    existingPagar.setData(pagar.getData());
                }
                if (pagar.getValor() != null) {
                    existingPagar.setValor(pagar.getValor());
                }
                if (pagar.getStatus() != null) {
                    existingPagar.setStatus(pagar.getStatus());
                }
                if (pagar.getObs() != null) {
                    existingPagar.setObs(pagar.getObs());
                }

                return existingPagar;
            })
            .map(pagarRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pagar> findAll(Pageable pageable) {
        log.debug("Request to get all Pagars");
        return pagarRepository.findAll(pageable);
    }

    public Page<Pagar> findAllWithEagerRelationships(Pageable pageable) {
        return pagarRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pagar> findOne(Long id) {
        log.debug("Request to get Pagar : {}", id);
        return pagarRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagar : {}", id);
        pagarRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagarDTO> findAllByDataInicialAndDataFinal(FiltroPagarDTO filtro) {
        log.debug("Request to get all Recebers by data início e data fim");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYY");

        List<PagarDTO> lista = pagarRepository
            .findAllByCriteria(filtro)
            .stream()
            .map(obj ->
                PagarDTO.getInstance(
                    obj.getId(),
                    obj.getData().format(formatter),
                    obj.getValor(),
                    obj.getStatus().toString(),
                    Objects.nonNull(obj.getTipoPagar()) ? obj.getTipoPagar().getNome() : "",
                    Objects.nonNull(obj.getPagarPara()) ? obj.getPagarPara().getNome() : "",
                    Objects.nonNull(obj.getDadosPessoais()) ? obj.getDadosPessoais().getNome() : "",
                    Objects.nonNull(obj.getTipoTransacao()) ? obj.getTipoTransacao().getNome() : "",
                    false
                )
            )
            .collect(Collectors.toList());
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Resource findAllByDataInicialAndDataFinalJasper(FiltroPagarDTO filtro) {
        //Gson gson = new Gson();
        log.debug(
            "Request to get jasper report of dataInicial: {}, dataFinal: {}, tipo: {}",
            filtro.getDataInicio(),
            filtro.getDataFim(),
            filtro.getTipo()
        );
        try {
            File file = ResourceUtils.getFile("classpath:templates/jasper/pagar/report_pagar.jrxml");
            //JasperReport jasperReport = (JasperReport)JRLoader.loadObject(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            //JRSaver.saveObject(jasperReport, "report_receber.jasper");

            List<PagarDTO> lista = this.findAllByDataInicialAndDataFinal(filtro);
            //String strJson = Objects.nonNull(dto)? gson.toJson(dto) : "";
            //log.debug("String JSON utilizada: {}", strJson);
            //InputStream stream = new ByteArrayInputStream(strJson.getBytes());
            //JsonDataSource dataSource = new JsonDataSource(stream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);

            Map<String, Object> parameters = new HashMap<>();
            //parameters.put("userName", "Dhaval's Orders");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ReportExporter simpleReportExporter = new ReportExporter(jasperPrint);
            String fileName = "";
            log.debug("Cria arquivo na pasta: {}", this.fileStorageLocation);

            switch (filtro.getTipo()) {
                case "PDF":
                case "PRINT":
                    fileName = "pagar.pdf";
                    //				JasperExportManager.exportReportToPdfFile(jasperPrint, this.fileStorageLocation + "/example.pdf");
                    simpleReportExporter.exportToPdf(this.fileStorageLocation + "/" + fileName, "HUNTERS");
                    break;
                case "XLSX":
                    fileName = "pagar.xlsx";
                    simpleReportExporter.exportToXlsx(this.fileStorageLocation + "/" + fileName, "HUNTERS");
                    break;
                case "CSV":
                    fileName = "pagar.csv";
                    simpleReportExporter.exportToCsv(this.fileStorageLocation + "/" + fileName);
                    break;
                default:
                    break;
            }
            log.debug("Lê arquivo {} na pasta: {}", fileName, this.fileStorageLocation);
            return loadFileAsResource(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private Resource loadFileAsResource(String fileName) throws IOException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }
}
