package com.prisma.sn.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.prisma.sn.model.Board;
import com.prisma.sn.model.Dice;
import com.prisma.sn.model.Ladder;
import com.prisma.sn.model.Player;
import com.prisma.sn.model.PlayerName;
import com.prisma.sn.model.Snake;
import com.prisma.sn.response.GameResponse;

@Service
public class SNServiceImpl implements SNService {
	Logger logger = LoggerFactory.getLogger(SNServiceImpl.class);
	private List<Snake> snakes;
	private List<Ladder> ladders;

	private Board board;
	private Dice dice;

	public void init() {
		snakes = new ArrayList<>();
		ladders = new ArrayList<>();
		snakes.add(new Snake(16, 6));
		snakes.add(new Snake(46, 25));
		snakes.add(new Snake(49, 11));
		snakes.add(new Snake(62, 19));
		snakes.add(new Snake(64, 60));
		snakes.add(new Snake(74, 53));
		snakes.add(new Snake(89, 68));
		snakes.add(new Snake(92, 88));
		snakes.add(new Snake(95, 75));
		snakes.add(new Snake(99, 80));

		ladders.add(new Ladder(2, 38));
		ladders.add(new Ladder(7, 14));
		ladders.add(new Ladder(8, 31));
		ladders.add(new Ladder(15, 26));
		ladders.add(new Ladder(21, 42));
		ladders.add(new Ladder(28, 84));
		ladders.add(new Ladder(36, 44));
		ladders.add(new Ladder(51, 67));
		ladders.add(new Ladder(71, 91));
		ladders.add(new Ladder(78, 98));
		ladders.add(new Ladder(87, 94));
		board = new Board(100);
		dice = new Dice(1, 6);
	}

	@Override
	public GameResponse playGame(List<PlayerName> playerList) {
		Queue<Player> players = new ArrayDeque<>();
		GameResponse resp = new GameResponse();
		if (!playerList.isEmpty() && playerList.size() > 1 && playerList.size() <= 4) {
			int breakCondition = playerList.size() - 1;
			for (PlayerName playerName : playerList) {
				players.add(new Player(playerName.getName(), 0, false));
			}
			// initialize the board with snakes and ladder
			init();
			// roll two dices. If the player throws doubles, then player can throw again.
			while (true) {
				Player player = players.poll();
				while (true) {
					dice.roll();
					int dice1Val = dice.getDice1Value();
					int dice2Val = dice.getDice2Value();
					dice.setResultVal(dice.getResultVal() + dice1Val + dice2Val);
					if (dice1Val != dice2Val) {
						break;
					}
				}
				int newPosition = player.getPosition() + dice.getResultVal();
				dice.setResultVal(0);
				playerTurns(players, resp, player, newPosition);
				if (players.size() <= breakCondition) {
					break;
				}
			}

		} else {
			if (playerList.size() == 1 || playerList.isEmpty()) {
				resp.setError("Minimum 2 players required");
			} else{
				resp.setError("Maximum 4 players are allowed");
			}

		}
		return resp;

	}

	private void playerTurns(Queue<Player> players, GameResponse resp, Player player, int newPosition) {
		if (newPosition > board.getEnd()) {
			player.setPosition(player.getPosition());
			players.offer(player);
		} else {
			player.setPosition(getNewPosition(newPosition));
			if (player.getPosition() == board.getEnd()) {
				player.setWon(true);
				resp.setName(player.getName());
				resp.setMessage(player.getName() + " " + "Won the game");
				logger.info("Player " + player.getName() + " Won.");
			} else {
				logger.info("Setting " + player.getName() + "'s new position to " + player.getPosition());
				players.offer(player);
			}
		}
	}

	private int getNewPosition(int newPosition) {
		for (Snake snake : snakes) {
			if (snake.getHead() == newPosition) {
				logger.info("Snake Bit");
				return snake.getTail();
			}
		}
		for (Ladder ladder : ladders) {
			if (ladder.getStart() == newPosition) {
				logger.info("Climbed ladder");
				return ladder.getEnd();
			}
		}
		return newPosition;
	}

}
