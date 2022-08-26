package it.uniroma3.spring.snake.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.uniroma3.spring.snake.security.PwdEncoder;

@Entity
@JsonIgnoreProperties({"id", "password", "enabled", "matches", "role"})
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Column(unique=true)
	@Size(min=3)
	private String username;
	@NotNull
	@Size(min=6)
	private String password;
	@NotNull
	@Column(unique=true)
	private String email;
	private boolean enabled;
	private Integer highScore;
	private Long playTime;
	private Integer kills;
	private String rank;
	private Integer elo;
	private Boolean promo;
	@OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Match> matches;
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;
	
	public User() {
		matches = new ArrayList<>();
		enabled = true;
		highScore = 0;
		playTime = Long.valueOf(0);
		kills = 0;
		promo = false;
	}
	
	public boolean checkPassword(String password) {
		return this.getPasswordEncoder().matches(password, this.password);
	}
	
	public void addMatchAndUpdateStats(Match match) {
		int score = match.getScore();
		if(score > this.highScore) {
			this.highScore = score;
		}
		
		this.playTime += match.getPlayingTime();
		this.kills += match.getKills();
		
		this.matches.add(match);
		match.setPlayer(this);
	}
	
	private PasswordEncoder getPasswordEncoder() {
		return PwdEncoder.getInstance().getPasswordEncoder();
	}
	
	public void encodePassword() {
		this.password = this.getPasswordEncoder().encode(this.password);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getHighScore() {
		return highScore;
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	public long getPlayTime() {
		return playTime;
	}
	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getElo() {
		return elo;
	}
	public void setElo(int elo) {
		this.elo = elo;
	}
	public Boolean getPromo() {
		return promo;
	}
	public void setPromo(Boolean promo) {
		this.promo = promo;
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
