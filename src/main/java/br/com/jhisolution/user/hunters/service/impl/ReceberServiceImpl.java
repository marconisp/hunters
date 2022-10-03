package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.report.util.ReportExporter;
import br.com.jhisolution.user.hunters.repository.ReceberRepository;
import br.com.jhisolution.user.hunters.service.ReceberService;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.ReceberDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.RelatorioReceberDTO;
import com.google.gson.Gson;
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
 * Service Implementation for managing {@link Receber}.
 */
@Service
@Transactional
public class ReceberServiceImpl implements ReceberService {

    private final Logger log = LoggerFactory.getLogger(ReceberServiceImpl.class);

    private final ReceberRepository receberRepository;
    private final Path fileStorageLocation;

    public ReceberServiceImpl(ReceberRepository receberRepository) {
        this.receberRepository = receberRepository;
        this.fileStorageLocation = Paths.get("./").toAbsolutePath().normalize();
    }

    @Override
    public Receber save(Receber receber) {
        log.debug("Request to save Receber : {}", receber);
        return receberRepository.save(receber);
    }

    @Override
    public Receber update(Receber receber) {
        log.debug("Request to save Receber : {}", receber);
        return receberRepository.save(receber);
    }

    @Override
    public Optional<Receber> partialUpdate(Receber receber) {
        log.debug("Request to partially update Receber : {}", receber);

        return receberRepository
            .findById(receber.getId())
            .map(existingReceber -> {
                if (receber.getData() != null) {
                    existingReceber.setData(receber.getData());
                }
                if (receber.getValor() != null) {
                    existingReceber.setValor(receber.getValor());
                }
                if (receber.getStatus() != null) {
                    existingReceber.setStatus(receber.getStatus());
                }
                if (receber.getObs() != null) {
                    existingReceber.setObs(receber.getObs());
                }

                return existingReceber;
            })
            .map(receberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Receber> findAll(Pageable pageable) {
        log.debug("Request to get all Recebers");
        return receberRepository.findAll(pageable);
    }

    public Page<Receber> findAllWithEagerRelationships(Pageable pageable) {
        return receberRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Receber> findOne(Long id) {
        log.debug("Request to get Receber : {}", id);
        return receberRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receber : {}", id);
        receberRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RelatorioReceberDTO findAllByDataInicialAndDataFinal(FiltroReceberDTO filtro) {
        log.debug("Request to get all Recebers by data início e data fim");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYY");

        List<ReceberDTO> lista = receberRepository
            .findAllByDataInicialAndDataFinal(filtro.getDataInicio(), filtro.getDataFim())
            .stream()
            .map(obj ->
                ReceberDTO.getInstance(
                    obj.getId(),
                    obj.getData().format(formatter),
                    obj.getValor(),
                    obj.getStatus().toString(),
                    Objects.nonNull(obj.getTipoReceber()) ? obj.getTipoReceber().getNome() : "",
                    Objects.nonNull(obj.getReceberDe()) ? obj.getReceberDe().getNome() : "",
                    Objects.nonNull(obj.getTipoTransacao()) ? obj.getTipoTransacao().getNome() : ""
                )
            )
            .collect(Collectors.toList());
        return RelatorioReceberDTO.getInstance(filtro.getDataInicio(), filtro.getDataFim(), lista);
    }

    @Override
    @Transactional(readOnly = true)
    public Resource findAllByDataInicialAndDataFinalJasper(FiltroReceberDTO filtro) {
        Gson gson = new Gson();
        log.debug(
            "Request to get jasper report of dataInicial: {}, dataFinal: {}, tipo: {}",
            filtro.getDataInicio(),
            filtro.getDataFim(),
            filtro.getTipo()
        );
        try {
            File file = ResourceUtils.getFile("classpath:templates/jasper/receber/report_receber.jrxml");
            //JasperReport jasperReport = (JasperReport)JRLoader.loadObject(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            //JRSaver.saveObject(jasperReport, "report_receber.jasper");

            RelatorioReceberDTO dto = this.findAllByDataInicialAndDataFinal(filtro);
            //String strJson = Objects.nonNull(dto)? gson.toJson(dto) : "";
            //log.debug("String JSON utilizada: {}", strJson);
            //InputStream stream = new ByteArrayInputStream(strJson.getBytes());
            //JsonDataSource dataSource = new JsonDataSource(stream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dto.getRecebers());

            Map<String, Object> parameters = new HashMap<>();
            //parameters.put("userName", "Dhaval's Orders");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ReportExporter simpleReportExporter = new ReportExporter(jasperPrint);
            String fileName = "";
            log.debug("Cria arquivo na pasta: {}", this.fileStorageLocation);

            switch (filtro.getTipo()) {
                case "PDF":
                case "PRINT":
                    fileName = "receber.pdf";
                    //				JasperExportManager.exportReportToPdfFile(jasperPrint, this.fileStorageLocation + "/example.pdf");
                    simpleReportExporter.exportToPdf(this.fileStorageLocation + "/" + fileName, "HUNTERS");
                    break;
                case "XLSX":
                    fileName = "receber.xlsx";
                    simpleReportExporter.exportToXlsx(this.fileStorageLocation + "/" + fileName, "HUNTERS");
                    break;
                case "CSV":
                    fileName = "receber.csv";
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
