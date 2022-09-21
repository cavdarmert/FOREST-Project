<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forest Hero Dashboard</title>
<link rel="stylesheet" href="/css/style.css"/>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>



<div class="outer-layer">
	<div class="stats-container">
		<div class="stats-picture-container">
			<div class="picture-sub-container">
				<div class="hero-picture-text">
				<p>Hero <c:out value="${hero.name}"></c:out></p>
				</div>
				<div>
				<img width="100" height="100" src="<c:url value="/images/hero.png" />" />
				</div>
				<div class="hero-picture-text">
				<p>LEVEL <c:out value="${hero.level}"></c:out></p>
			    </div>
		   </div>
		<div id="progress-bar-protection">
		<div class="progress position-relative" style="height: 30px; width:300px;">
     			<div class="progress-bar bg-danger" role="progressbar" aria-valuenow="${hero.health}" aria-valuemin="0" aria-valuemax="${hero.maxHealth}" style="width: ${(hero.health/hero.maxHealth)*100}%; color: black;">
    				<div class="justify-content-center d-flex position-absolute w-100 fw-bold ">
        			HP ${hero.health}/${hero.maxHealth}
    				</div> 
  				</div>
			</div>
			<div class="progress position-relative" style="height: 30px; width:300px;">
     			<div class="progress-bar" role="progressbar" aria-valuenow="${hero.experience}" aria-valuemin="0" aria-valuemax="${hero.nextLevelXpAmount()}" style="width: ${ ( hero.experience/hero.nextLevelXpAmount() ) * 100}%; color: black;">
    				<div class="justify-content-center d-flex position-absolute w-100 fw-bold ">
        			XP ${hero.experience}/${hero.nextLevelXpAmount()} TO LVL ${hero.level + 1}
    				</div> 
  				</div>
			</div>
			</div> 
		
		</div>
		
		<div class="first-and-second-main-container"></div>
		
			<div class="stats-first-container">
			
				<div class="first-sub-container">
					
					 <img width="45" height="45" src="<c:url value="/images/attack_power.png" />" />
					 <p>ATTACK POWER: ${hero.attackPower + hero.weaponAttackSum()} (${hero.attackPower}+${hero.weaponAttackSum()})</p>
				 </div>
				 <div class="first-sub-container">
					 <img width="45" height="45" src="<c:url value="/images/defense_power.png" />" />
					 <p>DEFENSE POWER: ${hero.defensePower + hero.armorDefenseSum()} (${hero.defensePower}+${hero.armorDefenseSum()})</p>
				 </div>
				 <div class="first-sub-container">
					 <img width="45" height="45" src="<c:url value="/images/critical_chance.png" />" />
					 <p>CRIT CHANCE: <c:out value="${hero.criticalChanceSum()}"></c:out>%</p>
				 </div>
				 
			</div>
			
			<div class="stats-second-container">
			
				<div class="second-sub-container">
				<img width="45" height="45" src="<c:url value="/images/gold.png" />" />
				<p>GOLD: <c:out value="${hero.gold}"></c:out></p>
				</div>
				<div class="second-sub-container">
				<img width="45" height="45" src="<c:url value="/images/diamond.png" />" />
		 		<p>DIAMOND: <c:out value="${hero.diamond}"></c:out></p>
		 		</div>
		 		
			</div>
		</div>
		
			<div class="stats-logout-container">
				<div>
				<a id="logout-button" href="/logout">Logout</a>
				</div>
			</div>
	</div>
	
	<div class="navigation-container">
		<div>
		<a href="/forest">
		<img width="25" height="25" src="<c:url value="/images/forest.png" />" />
		</a>
		<a href="/forest">Go to Forest</a>
		</div>
		<div>
		<img width="25" height="25" src="<c:url value="/images/trader.png" />" />
		<a href="/trader">Buy Items</a>
		</div>
		<div>
		<img width="25" height="25" src="<c:url value="/images/game.png" />" />
		<a href="/games">Go to Games</a>
		</div>
		<div>
		<img width="25" height="25" src="<c:url value="/images/item.png" />" />
		<a href="/show/inventory">Your Items</a>
		</div>
		<div>
		<img width="25" height="25" src="<c:url value="/images/guide.png" />" />
		<a href="/guide">Game Guide</a>
		</div>
	
	</div>
	
	<div class="places-container">
		<div class="single-place-container">
			<p><a href="/forest">Go to the Forest, fight monsters, earn some XP and Gold!</a></p>
			<div>
			<a href="/forest">
			<img width="200" height="200" src="<c:url value="/images/forest_big.png" />" />
			</a>
			</div>
		</div>
		
		<div class="single-place-container">
			<p><a href="/trader">Visit the Merchant to buy items.</a></p>
			<div>
			<a href="/trader">
			<img width="200" height="200" src="<c:url value="/images/merchant_big.png" />" />
			</a>
			</div>
			
		</div>
		
		<div class="single-place-container">
			<p><a href="/games">Looking for fun or Gold? Maybe Both, play some Games then!</a></p>
			<div>
			<a href="/games">
			<img width="200" height="200" src="<c:url value="/images/game_big.png" />" />
			</a>
			</div>
			
		</div>
	</div>

	<a href="/logout">Logout</a>
	<form:form action="/heal" method="post" modelAttribute="hero">
		<input type="hidden" name="_method" value="put">
		<form:input type="hidden" name="user" path="user"/>
		<input class="input" class="button" type="submit" value="Heal Your Hero for 20 Gold"/>
	</form:form>

</body>
</html>