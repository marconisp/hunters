package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.Foto;
import br.com.jhisolution.user.hunters.domain.FotoAvatar;
import br.com.jhisolution.user.hunters.domain.FotoIcon;
import br.com.jhisolution.user.hunters.domain.User;
import br.com.jhisolution.user.hunters.domain.util.UtilDomain;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.repository.FotoAvatarRepository;
import br.com.jhisolution.user.hunters.repository.FotoIconRepository;
import br.com.jhisolution.user.hunters.repository.FotoRepository;
import br.com.jhisolution.user.hunters.service.DadosPessoaisService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DadosPessoais}.
 */
@Service
@Transactional
public class DadosPessoaisServiceImpl implements DadosPessoaisService {

    private final Logger log = LoggerFactory.getLogger(DadosPessoaisServiceImpl.class);

    private final DadosPessoaisRepository dadosPessoaisRepository;
    private final FotoRepository fotoRepository;
    private final FotoIconRepository fotoIconRepository;
    private final FotoAvatarRepository fotoAvatarRepository;

    public DadosPessoaisServiceImpl(
        DadosPessoaisRepository dadosPessoaisRepository,
        FotoRepository fotoRepository,
        FotoIconRepository fotoIconRepository,
        FotoAvatarRepository fotoAvatarRepository
    ) {
        this.dadosPessoaisRepository = dadosPessoaisRepository;
        this.fotoRepository = fotoRepository;
        this.fotoIconRepository = fotoIconRepository;
        this.fotoAvatarRepository = fotoAvatarRepository;
    }

    @Override
    public DadosPessoais save(DadosPessoais dadosPessoais) {
        log.debug("Request to save DadosPessoais : {}", dadosPessoais);

        Long idFotoIcon = null;
        Long idFotoAvatar = null;

        BufferedImage fotoSmall = null;
        BufferedImage fotoMediu = null;

        FotoIcon fotoIcon = null;
        FotoAvatar fotoAvatar = null;

        try {
            Foto foto = dadosPessoais.getFoto();

            if (Objects.nonNull(foto)) {
                if (Objects.nonNull(dadosPessoais.getId())) {
                    Optional<DadosPessoais> dadosAux = dadosPessoaisRepository.findById(dadosPessoais.getId());
                    if (dadosAux.isPresent()) {
                        idFotoIcon = Objects.nonNull(dadosAux.get().getFotoIcon()) ? dadosAux.get().getFotoIcon().getId() : null;
                        idFotoAvatar = Objects.nonNull(dadosAux.get().getFotoAvatar()) ? dadosAux.get().getFotoAvatar().getId() : null;
                    }
                }

                if (Objects.nonNull(foto.getConteudo())) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(dadosPessoais.getFoto().getConteudo());
                    BufferedImage fotoAux = ImageIO.read(bais);

                    fotoSmall = Scalr.resize(fotoAux, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 60, 60, Scalr.OP_ANTIALIAS);
                    fotoMediu = Scalr.resize(fotoAux, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 200, 200, Scalr.OP_ANTIALIAS);

                    fotoIcon =
                        FotoIcon.getInstance(
                            idFotoIcon,
                            UtilDomain.convertBufferedImageToByte(fotoSmall, foto.getConteudoContentType()),
                            foto.getConteudoContentType()
                        );
                    fotoAvatar =
                        FotoAvatar.getInstance(
                            idFotoAvatar,
                            UtilDomain.convertBufferedImageToByte(fotoMediu, foto.getConteudoContentType()),
                            foto.getConteudoContentType()
                        );
                } else {
                    fotoIcon = FotoIcon.getInstance(idFotoIcon, new byte[0], "");
                    fotoAvatar = FotoAvatar.getInstance(idFotoAvatar, new byte[0], "");
                }

                Foto fotoSave = fotoRepository.save(foto);
                FotoIcon fotoIconSave = fotoIconRepository.save(fotoIcon);
                FotoAvatar fotoAvatarSave = fotoAvatarRepository.save(fotoAvatar);

                dadosPessoais.setFoto(fotoSave);
                dadosPessoais.setFotoIcon(fotoIconSave);
                dadosPessoais.setFotoAvatar(fotoAvatarSave);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dadosPessoaisRepository.save(dadosPessoais);
    }

    @Override
    public DadosPessoais update(DadosPessoais dadosPessoais) {
        log.debug("Request to save DadosPessoais : {}", dadosPessoais);
        return dadosPessoaisRepository.save(dadosPessoais);
    }

    @Override
    public Optional<DadosPessoais> partialUpdate(DadosPessoais dadosPessoais) {
        log.debug("Request to partially update DadosPessoais : {}", dadosPessoais);

        return dadosPessoaisRepository
            .findById(dadosPessoais.getId())
            .map(existingDadosPessoais -> {
                if (dadosPessoais.getNome() != null) {
                    existingDadosPessoais.setNome(dadosPessoais.getNome());
                }
                if (dadosPessoais.getSobreNome() != null) {
                    existingDadosPessoais.setSobreNome(dadosPessoais.getSobreNome());
                }
                if (dadosPessoais.getPai() != null) {
                    existingDadosPessoais.setPai(dadosPessoais.getPai());
                }
                if (dadosPessoais.getMae() != null) {
                    existingDadosPessoais.setMae(dadosPessoais.getMae());
                }
                if (dadosPessoais.getTelefone() != null) {
                    existingDadosPessoais.setTelefone(dadosPessoais.getTelefone());
                }
                if (dadosPessoais.getCelular() != null) {
                    existingDadosPessoais.setCelular(dadosPessoais.getCelular());
                }
                return existingDadosPessoais;
            })
            .map(dadosPessoaisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosPessoais> findAll(Pageable pageable) {
        log.debug("Request to get all DadosPessoais");
        return dadosPessoaisRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosPessoais> findAllByUser(Pageable pageable, User user) {
        log.debug("Request to get all DadosPessoais by User");
        return dadosPessoaisRepository.findAllByUser(pageable, user);
    }

    public Page<DadosPessoais> findAllWithEagerRelationships(Pageable pageable) {
        return dadosPessoaisRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DadosPessoais> findOne(Long id) {
        log.debug("Request to get DadosPessoais : {}", id);
        return dadosPessoaisRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DadosPessoais : {}", id);
        dadosPessoaisRepository.deleteById(id);
    }
}
