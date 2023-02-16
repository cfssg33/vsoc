package com.autocrypt.mon.policy.repository;

import com.autocrypt.mon.policy.model.policyLog;
import com.autocrypt.mon.policy.repository.entity.HostPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HostPolicyRepository extends JpaRepository<HostPolicyInfo, Integer> {

    @Query(value = "select *, max(p.version) as maxVersion  from vsoc_host_policy as p group by p.name", nativeQuery = true)
    List<policyLog> findAllByGroupbyNameAndMaxVersion();
}
