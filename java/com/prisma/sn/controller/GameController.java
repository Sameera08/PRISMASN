package com.prisma.sn.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prisma.sn.model.PlayerName;
import com.prisma.sn.response.GameResponse;
import com.prisma.sn.service.SNService;

@RestController
public class GameController {
	@Autowired
	private SNService snService;
	@PostMapping("/game")
	public ResponseEntity<GameResponse> playGame(@RequestBody List<PlayerName> players) {
		ResponseEntity<GameResponse> returnedResponse=null;
		GameResponse response=snService.playGame(players);
		if(!StringUtils.isBlank(response.getName())) {
			returnedResponse=new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			returnedResponse=new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return returnedResponse;
	}

}
