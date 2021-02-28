package com.insurance.application.controller;

import com.insurance.application.dto.Response;
import com.insurance.application.exception.AccessDeniedException;
import com.insurance.application.exception.NotFoundException;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/insurances")
public class DeleteInsuranceController {

    private UserService userService;

    @Autowired
    public DeleteInsuranceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public Response deleteUserInsurance(@PathVariable Integer id) throws Exception {
        Response response = new Response(true);
        try {
            if (id != 0 && userService.findById(id) != null) {
                userService.deleteById(id);
            }
            response.setMessage("Xóa thành công");
        } catch (AccessDeniedException Ae) {
            response.setStatus(false);
            throw new AccessDeniedException("Eror! Bạn không có quyền delete");
        } catch (NotFoundException fx) {
            response.setStatus(false);
            throw new NotFoundException("record không tồn tại");
        } catch (Exception ex) {
            response.setStatus(false);
            throw new Exception(ex.getMessage());
        }
        return response;
    }
}
