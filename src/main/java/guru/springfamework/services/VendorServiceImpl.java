package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
            vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
            return vendorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
            vendorDTO.setVendorUrl(getVendorUrl(id));
            return vendorDTO;
        }).orElse(null);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor detachedVendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnDTO(detachedVendor);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) throws ResourceNotFoundException {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName() != ""){
                vendor.setName(vendorDTO.getName());
            }
            return saveAndReturnDTO(vendor);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    public VendorDTO saveAndReturnDTO(Vendor vendor){
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDto(savedVendor);
        savedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedVendorDTO;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    public String getVendorUrl(Long id) {
        return VendorController.BASEURL + "/" + id.toString();
    }
}
