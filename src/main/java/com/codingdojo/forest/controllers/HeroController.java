package com.codingdojo.forest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.forest.models.Game;
import com.codingdojo.forest.models.Hero;
import com.codingdojo.forest.models.Item;
import com.codingdojo.forest.models.Monster;
import com.codingdojo.forest.services.GameService;
import com.codingdojo.forest.services.HeroService;
import com.codingdojo.forest.services.ItemService;
import com.codingdojo.forest.services.MonsterService;
import com.codingdojo.forest.services.UserService;

@Controller
public class HeroController {
	@Autowired
	private HeroService heroService;
	@Autowired
	private UserService userService;
	@Autowired
	private MonsterService monsterService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private GameService gameService;
	
	
	public static ArrayList<String> actions = new ArrayList<>();
	
	public static Boolean isWin;
	
	public static Boolean isEligibleToAddItem;
	
	public static Boolean isGameWin;

	
	@GetMapping("/dashboard")
	public String heroDashboard(HttpSession session, Model model) {
	 
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");

		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		model.addAttribute("user", userService.findById(userId));
		 
		return "dashboard.jsp";
	}
	
	@GetMapping("/forest")
	public String showForest(HttpSession session, Model model) {
	 
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");

		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		model.addAttribute("user", userService.findById(userId));
		
		List<Monster> allMonsters = monsterService.allMonsters();
		model.addAttribute("allMonsters", allMonsters);
		 
		return "forest.jsp";
	}
	
	@GetMapping("/trader")
	public String showTrader(HttpSession session, Model model) {
	 
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");

		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		model.addAttribute("user", userService.findById(userId));
		
		List<Item> allItems = itemService.allItems();
		model.addAttribute("allItems", allItems);
		 
		return "trader.jsp";
	}
	
	@GetMapping("/forest/fight/{id}")
	public String beforeFight(@PathVariable("id") Long id, HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		 
		Monster monster = monsterService.findMonster(id);
		model.addAttribute("monster", monster);

		return "forest_fight.jsp";
	}
	
	@GetMapping("/trader/buy/{id}")
	public String beforeBuy(@PathVariable("id") Long id, HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		 
		Item item = itemService.findItem(id);
		model.addAttribute("item", item);
		
		if(hero.getGold()<item.getPrice()) {
			return "redirect:/trader";
		}
		
		return "item_buy.jsp";
	}
	
	@GetMapping("/forest/fight/{id}/result")
	public String showFightResult(@PathVariable("id") Long id, HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		 
		Monster monster = monsterService.findMonster(id);
		model.addAttribute("monster", monster);
		
		model.addAttribute("actions", actions);
		String result = "";
		if (isWin ==true) {
			result = "You WON!";
		} else result= "You LOST!";
		
		model.addAttribute("result", result);


		
		return "forest_fight_result.jsp";
	}
	
	@GetMapping("/trader/buy/{id}/result")
	public String showBuyResult(@PathVariable("id") Long id, HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		 
		Item item = itemService.findItem(id);
		model.addAttribute("item", item);
		
		String itemBuyResult = "";
		if (isEligibleToAddItem ==true) {
			itemBuyResult = "YOU SUCCESSFULLY BOUGHT " + item.getName();
		} else itemBuyResult= "YOU CAN'T BUY THE ITEM OF SAME TYPE FOR" + item.getCategory() + "REMOVE IT FIRST";
		
		model.addAttribute("itemBuyResult", itemBuyResult);
		
		return "item_buy_result.jsp";
	}
	
	@PutMapping("/forest/fight/{id}")
	public String updateAfterMonsterFight(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("hero") Hero hero, 
			BindingResult result, 
			HttpSession session,
			Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");

		 
		if(result.hasErrors()) {
			
			Hero againShowHero = heroService.myHero(userService.findById(userId));
			model.addAttribute("hero", againShowHero);
			
			return "forest_fight.jsp";
		}
		else {
			hero = heroService.myHero(userService.findById(userId));
			Monster monster = monsterService.findMonster(id);
//			model.addAttribute("monster", monster);
			
			
			isWin = hero.attackToMonster(monster, actions);
			hero.levelUpActions();
			heroService.updateHero(hero);
			return "redirect:/forest/fight/{id}/result";
		}
	}
	
	@PutMapping("/trader/buy/{id}")
	public String updateAfterBuyingItem(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("hero") Hero hero, 
			BindingResult result, 
			HttpSession session,
			Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");

		 
		if(result.hasErrors()) {
			
			Hero againShowHero = heroService.myHero(userService.findById(userId));
			model.addAttribute("hero", againShowHero);
			
			return "trader_buy.jsp";
		}
		else {
			hero = heroService.myHero(userService.findById(userId));
			Item item = itemService.findItem(id);
			
			isEligibleToAddItem = hero.canAddItemToInventory(item);
			hero.addItemToInventory(item);
			heroService.updateHero(hero);
			return "redirect:/trader/buy/{id}/result";
		}
	}
	
	@GetMapping("/show/inventory")
	public String showItemInventory(HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		
		List<Item> myItems = itemService.myItems(hero);
		model.addAttribute("myItems", myItems);
		
		return "show_inventory.jsp";
	}
	
	
	@PutMapping("/item/remove/{id}")
	public String removeItem(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("hero") Hero hero, 
			BindingResult result, 
			HttpSession session,
			Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");

		 
		if(result.hasErrors()) {
			
			Hero againShowHero = heroService.myHero(userService.findById(userId));
			model.addAttribute("hero", againShowHero);
			
			return "trader_buy.jsp";
		}
		else {
			hero = heroService.myHero(userService.findById(userId));
			Item item = itemService.findItem(id);
			
			isEligibleToAddItem = hero.canAddItemToInventory(item);
			hero.removeItemFromInventory(item);
			heroService.updateHero(hero);
			return "redirect:/show/inventory";
		}
	}
	
	@GetMapping("/games")
	public String showGames(HttpSession session, Model model) {
	 
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Long userId = (Long) session.getAttribute("userId");

		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		model.addAttribute("user", userService.findById(userId));
		
		List<Game> allGames = gameService.allGames();
		model.addAttribute("allGames", allGames);
		 
		return "games.jsp";
	}
	
	@PutMapping("/games/play/{id}")
	public String updateAfterGame(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("hero") Hero hero, 
			BindingResult result, 
			HttpSession session,
			Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");

		 
		if(result.hasErrors()) {
			
			Hero againShowHero = heroService.myHero(userService.findById(userId));
			model.addAttribute("hero", againShowHero);
			
			return "games.jsp";
		}
		else {
			hero = heroService.myHero(userService.findById(userId));
			Game game = gameService.findGame(id);
			
//			isWin = hero.attackToMonster(monster, actions);
			
			if (id == 1) {
				isGameWin = game.playGoldtoGold10PercentChance(hero);
				
			} 
			else if (id == 2) {
				isGameWin = game.playGoldToGold50PercentChance(hero);
				
			} 
			else if (id == 3) {
				isGameWin = game.playDiamondToGold25PercentChance(hero);
				
			}else if (id == 4) {
				isGameWin = game.playGoldToDiamond25PercentChance(hero);
				
			}
			
			heroService.updateHero(hero);
			return "redirect:/games/play/{id}/result";
		}
	}
	
	@GetMapping("/games/play/{id}/result")
	public String showGameResult(@PathVariable("id") Long id, HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		 
		Game game = gameService.findGame(id);
		model.addAttribute("game", game);
		
		String gameResult = "";
		if (isGameWin ==true) {
			gameResult = "YOU WON " + game.getRewardAmount() + " " + game.getRewardType() + "!";
		} else gameResult = "YOU LOST " + game.getTicketPrice() + " " + game.getPriceType() + "!";

		
		model.addAttribute("gameResult", gameResult);
		
		return "game_result.jsp";
	}
	
	
	
	
	
	
	@GetMapping("/guide")
	public String showGameGuide(HttpSession session, Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Hero hero = heroService.myHero(userService.findById(userId));
		model.addAttribute("hero", hero);
		
		
		List<Item> myItems = itemService.myItems(hero);
		model.addAttribute("myItems", myItems);
		
		return "guide.jsp";
	}
	
	
	@PutMapping("/heal")
	public String heal(
			@Valid @ModelAttribute("hero") Hero hero, 
			BindingResult result, 
			HttpSession session,
			Model model) {	
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");

		 
		if(result.hasErrors()) {
			
			Hero againShowHero = heroService.myHero(userService.findById(userId));
			model.addAttribute("hero", againShowHero);
			
			return "dashboard.jsp";
		}
		else {
			hero = heroService.myHero(userService.findById(userId));			
			
			hero.healHeroWithGold();
			heroService.updateHero(hero);
			return "redirect:/dashboard";
		}
	}
	
	

}
