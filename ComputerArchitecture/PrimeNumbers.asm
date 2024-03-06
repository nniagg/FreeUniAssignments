.data
N_prompt: .asciiz "Enter N: "
M_prompt: .asciiz "Enter M: "
prime_output: .asciiz "Prime numbers between N and M: "
space: .asciiz " "
newline: .asciiz "\n"

.text
.globl main

main:
    # Prompt for N
    li $v0, 4           # Print string syscall code
    la $a0, N_prompt    # Load address of N prompt
    syscall

    # Read N
    li $v0, 5           # Read integer syscall code
    syscall
    move $s0, $v0       # Store N in $s0

    # Prompt for M
    li $v0, 4           # Print string syscall code
    la $a0, M_prompt    # Load address of M prompt
    syscall

    # Read M
    li $v0, 5           # Read integer syscall code
    syscall
    move $s1, $v0       # Store M in $s1

    # Print prime numbers
    li $v0, 4           # Print string syscall code
    la $a0, prime_output # Load address of prime numbers output string
    syscall

    # Loop through the range [N, M]
    move $t0, $s0       # Initialize i = N
    loop:
        bgt $t0, $s1, end_loop  # Exit loop if i > M
        
        # Check if i is prime
        li $t1, 0           # Initialize counter = 0
        li $t2, 2           # Initialize k = 2
        prime_loop:
            mul $t3, $t2, $t2   # Calculate k * k
            bgt $t3, $t0, check_prime  # If k * k > i, check if i is prime
            div $t0, $t2         # Divide i by k
            mfhi $t4            # Remainder stored in $t4
            beqz $t4, not_prime # If remainder is zero, i is divisible by k (not prime)
            addi $t2, $t2, 1    # Increment k
            j prime_loop        # Continue loop
        check_prime:
            beq $t1, $zero, print_prime # If counter is zero, i is prime
            addi $t0, $t0, 1    # Increment i
            j loop              # Continue loop
        not_prime:
            addi $t1, $t1, 1    # Increment counter
            addi $t0, $t0, 1    # Increment i
            j loop              # Continue loop

    print_prime:
        li $v0, 1           # Print integer syscall code
        move $a0, $t0       # Load prime number to print
        syscall
        li $v0, 4           # Print string syscall code
        la $a0, space       # Load address of space string
        syscall
        j end_print_prime   # End print prime loop

    end_print_prime:
        addi $t0, $t0, 1    # Increment i
        j loop              # Continue loop

    end_loop:
    li $v0, 4           # Print string syscall code
    la $a0, newline     # Load address of newline string
    syscall

    # Exit program
    li $v0, 10          # Exit syscall code
    syscall

