
package com.ecommerce.application.controller;

import com.ecommerce.application.handler.ResponseHandler;
import com.ecommerce.application.model.Voucher;
import com.ecommerce.application.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllVouchers(){
        try
        {
            List<Voucher> result = voucherService.getAllVouchers();
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createVoucher(@RequestBody List<Voucher> voucher){
        try
        {
            List<Voucher> result = voucherService.createVoucher(voucher);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}


