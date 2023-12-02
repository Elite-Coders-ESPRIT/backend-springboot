package tn.esprit.spring.Service;

import tn.esprit.spring.Repository.BlocRepository;
import tn.esprit.spring.Repository.FoyerRepository;
import tn.esprit.spring.Repository.UniversiteRepository;
import tn.esprit.spring.entity.Bloc;
import tn.esprit.spring.entity.Foyer;
import tn.esprit.spring.entity.TypeC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entity.Universite;

import java.util.List;
import java.util.Optional;

@Service
public class FoyerService implements FoyerServiceImpl {
    @Autowired
    FoyerRepository foyerRepository;
    @Autowired
    UniversiteRepository universiteRepository;
    @Autowired
    BlocRepository blocRepository;

    @Override
    public Foyer addFoyer(Foyer foyer) { return foyerRepository.save(foyer); }
    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }
    @Override
    public Foyer updateFoyer(Foyer foyer) {
        if (foyerRepository.findById(foyer.getIdFoyer()).isPresent()) {
            Foyer fToUpdate = foyerRepository.findById(foyer.getIdFoyer()).get();
            if(foyer.getUniversite()!= null){
                Universite u = universiteRepository.findById(foyer.getUniversite().getIdUniversite()).get();
                u.setFoyer(null);
                universiteRepository.save(u);
            }
            fToUpdate.setUniversite(foyer.getUniversite());
            foyerRepository.save(fToUpdate);
            fToUpdate.setUniversite(foyer.getUniversite());
            fToUpdate.setCapaciteFoyer(foyer.getCapaciteFoyer());
            fToUpdate.setNomFoyer(foyer.getNomFoyer());
            fToUpdate.setBlocs(foyer.getBlocs());
            return foyerRepository.save(foyer);
        }
        return null;
    }
    @Override
    public void deleteFoyer(Long id) { foyerRepository.deleteById(id); }

    @Override
    public List<Foyer> findFoyersByTypeChambre(TypeC TypeChambre) {
        return foyerRepository.findFoyersByTypeChambre(TypeChambre);
    }

    @Override
    public Foyer findFoyerById(Long id){
        return foyerRepository.findById(id).get();
    }

    @Override
    public long findCapaciteFoyerByIdBloc(Long Idbloc) {
        return foyerRepository.findCapaciteFoyerByIdBloc(Idbloc);
    }
    @Override
    public List<Universite> findAllUniversiteNoAffecte() {
        return foyerRepository.findAllUniversiteNoAffecte();
    }
    @Override
    public List<Bloc> FindAllBlocsNull() {
        return foyerRepository.FindAllBlocsNull();
    }
    @Override
    public Optional<Foyer> findFoyerByIdf(Long idFoyer) {
        return foyerRepository.findById(idFoyer);
    }
    @Override
    public String assignBlocsToFoyersById(Long idFoyer, List<Bloc> blocs) {
        Foyer f = foyerRepository.findById(idFoyer).orElse(null);
        if (f != null && !blocs.isEmpty()) {
            for (Bloc b : blocs) {
                b.setFoyers(f);
                blocRepository.save(b);
            }
            return "Blocs assigned to Foyer successfully!";
        } else {
            return "Foyer not found or no blocs provided!";
        }
    }
    @Override
    public List<Object[]> getNombreEtudiantsParFoyer() {
        return foyerRepository.getNombreEtudiantsParFoyer();
    }
    @Override
    public List<Object[]> getNombreChambresParFoyer() {
        return foyerRepository.getNombreChambresParFoyer();
    }
    @Override
    public List<Object[]> getNombreChambresParFoyerByType() {
        return foyerRepository.getNombreChambresParFoyerByType();
    }
}
