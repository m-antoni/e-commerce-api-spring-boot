
package com.ecommerce.application.service;

import com.ecommerce.application.model.Voucher;
import com.ecommerce.application.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers(){
        List<Voucher> vouchers =  new ArrayList<>();
        voucherRepository.findAll().forEach(vouchers::add);
        return vouchers;
    }

    public List<Voucher> createVoucher(List<Voucher> voucher){
        return voucherRepository.saveAll(voucher);
    }


}

