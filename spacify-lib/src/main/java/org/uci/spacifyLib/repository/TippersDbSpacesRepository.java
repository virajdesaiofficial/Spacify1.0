package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.TippersDbSpaceEntity;

import java.util.List;

@Repository
public interface TippersDbSpacesRepository extends JpaRepository<TippersDbSpaceEntity, Integer> {

    List<TippersDbSpaceEntity> findDistinctByspaceType(String spaceType);

    List<TippersDbSpaceEntity> findByBuildingIdAndSpaceTypeNotIn(Integer buildingId, List<String> spaceTypeList);
}
