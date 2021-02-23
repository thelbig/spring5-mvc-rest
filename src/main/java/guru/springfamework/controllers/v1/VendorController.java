package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VendorController.BASEURL)
public class VendorController {

    public static final String BASEURL = "/api/v1/vendors";

    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VendorDTO> getAll() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getOne(@PathVariable String id) {
        return vendorService.getVendorById(Long.parseLong(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO putVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(Long.parseLong(id), vendorDTO);
    }

    @PatchMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(Long.parseLong(id), vendorDTO);
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.parseLong(id));
    }
}
