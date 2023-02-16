package com.autocrypt.mon.policy.repository;

import com.autocrypt.mon.policy.model.policyLog;
import com.autocrypt.mon.policy.repository.entity.CanPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CanPolicyRepository extends JpaRepository<CanPolicyInfo, Integer> {

    @Query(value = "select *, max(p.version) as maxVersion  from vsoc_can_policy as p group by p.name", nativeQuery = true)
    List<policyLog> findAllByGroupbyNameAndMaxVersion();


}
