package com.msvc_aws.proyecto00.file_service.Respository;

import com.msvc_aws.proyecto00.file_service.Model.AppFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FIleRepository extends JpaRepository<AppFile,String> {


    List<AppFile> findAllByUserid(Integer userid);
}
