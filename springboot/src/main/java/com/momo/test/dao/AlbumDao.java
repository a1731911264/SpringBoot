package com.momo.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.momo.test.pojo.Album;
import com.momo.test.pojo.User;

public interface AlbumDao extends JpaRepository<Album, String> {

}
