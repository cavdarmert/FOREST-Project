<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forest Fight Result</title>
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
		
		<div class="progress position-relative" style="height: 25px; width:300px;">
     			<div class="progress-bar bg-danger" role="progressbar" aria-valuenow="${hero.health}" aria-valuemin="0" aria-valuemax="${hero.maxHealth}" style="width: ${(hero.health/hero.maxHealth)*100}%; color: black;">
    				<div class="justify-content-center d-flex position-absolute w-100 fw-bold ">
        			HP ${hero.health}/${hero.maxHealth}
    				</div> 
  				</div>
			</div>
			<div class="progress position-relative" style="height: 25px; width:300px;">
     			<div class="progress-bar" role="progressbar" aria-valuenow="${hero.experience}" aria-valuemin="0" aria-valuemax="${hero.nextLevelXpAmount()}" style="width: ${ ( hero.experience/hero.nextLevelXpAmount() ) * 100}%; color: black;">
    				<div class="justify-content-center d-flex position-absolute w-100 fw-bold ">
        			XP ${hero.experience}/${hero.nextLevelXpAmount()} TO LVL ${hero.level + 1}
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
				<a id="logout-button" href="/logout">Logout</a>
			</div>
	</div>
	
	<div class="navigation-container">
		<div>
		<a href="/dashboard">
		<img width="25" height="25" src="<c:url value="/images/hero.png" />" />
		</a>
		<a href="/forest">Back to Dashboard</a>
		</div>
		<div>
		<a href="/forest">
		<img width="25" height="25" src="<c:url value="/images/forest.png" />" />
		</a>
		<a href="/forest">Back to Forest</a>
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
	
	<div class="fight-container">
		<div>
			<div>
				<img width="100" height="100" src="<c:url value="/images/hero.png" />" />
			</div>
			<p>HERO <c:out value="${hero.name}"></c:out></p>
		</div>
		
			<p id="against">AGAINST</p>
			
		<div>
			<div>
				<img width="100" height="100" src="<c:url value="/images/forest.png" />" />
			</div>
			<p><c:out value="${monster.name}"></c:out></p>
		</div>
	</div>
	
	<div class="fight-result-main">
		<div class="fight-result">
		<c:forEach var="action" items="${actions}">
		<p><c:out value="${action}"></c:out></p>				
		</c:forEach>
		<p>FIGHT RESULT: <c:out value="${result}"></c:out></p>
		<p>XP EARNED: <c:out value="${monster.xpReward}"></c:out></p>
		<p>GOLD EARNED: <c:out value="${monster.goldReward}"></c:out></p>
		<p>DIAMOND EARNED: <c:out value="${monster.diamondReward}"></c:out></p>
		</div>
	</div>
	<div id="align-button">
		<a id="logout-button" href="/forest">Back to Forest</a>
	</div>
</body>
</html>