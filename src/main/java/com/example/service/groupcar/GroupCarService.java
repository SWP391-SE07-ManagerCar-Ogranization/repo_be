package com.example.service.groupcar;

import com.example.entity.GroupCar;
import com.example.repository.GroupCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupCarService {
    @Autowired
    private GroupCarRepository groupCarRepository;

    public GroupCar saveGroupCar(GroupCar groupCar) {
        return groupCarRepository.save(groupCar);
    }

    public List<GroupCar> saveGroupCars(List<GroupCar> groupCars) {
        return groupCarRepository.saveAll(groupCars);
    }

    public List<GroupCar> getGroupCars(){
            return groupCarRepository.findAll();
    }

    public List<GroupCar> getGroupCarsByStartPoint(String startPoint){
        return groupCarRepository.findByStartPoint(startPoint);
    }

    public List<GroupCar> getGroupCarsByEndPoint(String endPoint){
        return groupCarRepository.findByEndPoint(endPoint);
    }


    public GroupCar getGroupCarById(int id) {
        return groupCarRepository.findById(id).orElse(null);
    }

    public GroupCar getGroupCarByGroupName(String groupName) {
        return groupCarRepository.findByGroupName(groupName);
    }


    public String deleteGroupCar(int id){
        groupCarRepository.deleteById(id);
        return "GroupCar removed!!" + id;
    }
    public GroupCar updateGroupCar(GroupCar groupCar){
        GroupCar existingGroupCar = groupCarRepository.findById(groupCar.getGroupId()).orElse(null);
        existingGroupCar.setGroupId(groupCar.getGroupId());
        existingGroupCar.setGroupName(groupCar.getGroupName());
        existingGroupCar.setCapacity(groupCar.getCapacity());
        existingGroupCar.setCreateAt(groupCar.getCreateAt());
        existingGroupCar.setUpdateAt(groupCar.getUpdateAt());
        existingGroupCar.setStartPoint(groupCar.getStartPoint());
        existingGroupCar.setEndPoint(groupCar.getEndPoint());
        existingGroupCar.setFinish(groupCar.isFinish());
        existingGroupCar.setTimeStart(groupCar.getTimeStart());
        existingGroupCar.setCustomers(groupCar.getCustomers());
        return groupCarRepository.save(existingGroupCar);
    }
    public void addCustomerGroupCar(int customerId, int groupId){
        groupCarRepository.GroupCarJoin(customerId, groupId);
    }


}
